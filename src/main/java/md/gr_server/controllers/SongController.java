package md.gr_server.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.gr_server.services.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("songs")
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    @GetMapping(value = "{songId}")
    @ResponseBody
    public ResponseEntity<Object> findSong (
            @PathVariable("songId") Long songId
    ) {
        return ResponseEntity.ok(songService.findSongDto(songId));
    }
}
