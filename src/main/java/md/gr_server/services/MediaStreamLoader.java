package md.gr_server.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;

public interface MediaStreamLoader {
    ResponseEntity<StreamingResponseBody> loadMediaFile
            (String localMediaFilePath, String rangeValues) throws IOException;
}
