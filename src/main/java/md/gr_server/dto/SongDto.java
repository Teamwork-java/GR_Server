package md.gr_server.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import md.gr_server.entities.Song;

import java.io.Serializable;

/**
 * A DTO for the {@link md.gr_server.entities.Song} entity
 */
@Data
@RequiredArgsConstructor
public class SongDto implements Serializable {
    private final Long id;
    private final String title;
    private final Long playsCount;
    private final String songPath;
    private final String clipPath;

    public SongDto(Song song) {
        this.id = song.getId();
        this.title = song.getTitle();
        this.playsCount = song.getPlaysCount();
        this.songPath = song.getSongPath();
        this.clipPath = song.getClipPath();
    }
}