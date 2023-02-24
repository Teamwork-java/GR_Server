package md.gr_server.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import md.gr_server.dto.SongDto;
import md.gr_server.repositories.SongRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    public SongDto findSongDto(Long songId) {
        return new SongDto(songRepository.findById(songId).orElseThrow(EntityNotFoundException::new));
    }

    public String findSongPath(Long songId) {
        return songRepository.findById(songId).orElseThrow(
                EntityNotFoundException::new
        ).getSongPath();
    }
}
