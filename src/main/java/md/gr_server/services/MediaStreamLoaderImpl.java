package md.gr_server.services;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class MediaStreamLoaderImpl implements MediaStreamLoader {
    private final SongService songService;
    private final String songPackage;
    private final String clipPackage;

    public MediaStreamLoaderImpl(
            SongService songService,
            @Value("${media.audio.package}") String songPackage,
            @Value("${media.video.package}") String clipPackage) {
        this.songService = songService;
        this.songPackage = songPackage;
        this.clipPackage = clipPackage;
    }

    @Override
    public ResponseEntity<StreamingResponseBody> loadMediaFile(
            @NotNull(message = "SongId required")
            @Min(value = 1, message = "SongId must be >= 1") Long songId,
            String rangeHeader
    ) throws IOException {
        String songPath = songService.findSongPath(songId);
        String filePathString = songPackage + songPath;

        Path filePath = Path.of(filePathString);
        long fileSize = Files.size(filePath);
        if (rangeHeader == null || rangeHeader.isBlank()) {
            return loadMediaFile(filePathString, 0, fileSize - 1, fileSize);
        }

        String[] ranges = rangeHeader.split("-");
        for (int i = 0; i < ranges.length; i++) {
            ranges[i] = ranges[i].replaceAll("[^0-9]", "");
        }
        long rangeStart = (ranges[0].matches("[0-9]+")) ? Long.parseLong(ranges[0]) : 0L;
        long rangeEnd = fileSize - 1;
        if (ranges.length > 1 && ranges[1].matches("[0-9]+")) {
            rangeEnd = Long.parseLong(ranges[1]);
        }

        if (rangeStart < 0) {
            rangeStart = 0L;
        }
        if (rangeEnd == 0L && fileSize > 0L || rangeEnd > fileSize) {
            rangeEnd = fileSize - 1;
        }

        return loadMediaFile(filePathString, rangeStart, rangeEnd, fileSize);
    }

    private ResponseEntity<StreamingResponseBody> loadMediaFile(
            String filePathString,
            final long rangeStart,
            final long rangeEnd,
            final long fileSize
    ) {
        StreamingResponseBody responseStream;
        byte[] buffer = new byte[2048];
        final HttpHeaders responseHeaders = new HttpHeaders();

        String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
        // TODO: Hardcoded video/x-mpeg ! Change to be flexible.
        responseHeaders.add("Content-Type", "video/x-mpeg");
        responseHeaders.add("Content-Length", contentLength);
        responseHeaders.add("Accept-Ranges", "bytes");
        responseHeaders.add("Content-Range", "bytes" + " " +
                rangeStart + "-" + rangeEnd + "/" + fileSize);

        responseStream = os -> {
            try (RandomAccessFile file = new RandomAccessFile(filePathString, "r")) {
                long pos = rangeStart;
                file.seek(pos);
                while (pos < rangeEnd) {
                    file.read(buffer);
                    os.write(buffer);
                    pos += buffer.length;
                }
                os.flush();
            }
        };

        return new ResponseEntity<>(responseStream, responseHeaders, HttpStatus.PARTIAL_CONTENT);
    }
}
