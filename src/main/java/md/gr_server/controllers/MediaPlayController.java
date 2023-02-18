package md.gr_server.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.gr_server.services.MediaStreamLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;

@Slf4j
@RestController
@RequestMapping("media")
@RequiredArgsConstructor
public class MediaPlayController {

    private final MediaStreamLoader mediaLoaderService;

    @GetMapping(value = "video")
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> playVideo (
            @RequestParam("songId") String songId,
            @RequestHeader(value = "Range", required = false) String rangeHeader
    ) {
        // TODO: Get media package path string from application.properties
        // TODO: Get media name from Database instead of hardcoded SongId
        String filePathString = "D:\\Documents\\Java\\Spring\\GR_Server\\src\\main\\resources\\static\\video\\" + songId;
        try {
            return mediaLoaderService.loadMediaFile(filePathString, rangeHeader);
        } catch (InvalidPathException e) {
            log.error("Invalid media file path" + e.getReason());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (FileNotFoundException e) {
            log.error("File Not Found: " + filePathString);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            log.error(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
