package md.gr_server.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "songs")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "song_id", nullable = false)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    private Long playsCount;

    private String songPath;

    private String clipPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    @ToString.Exclude
    private Album album;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "songs")
    @ToString.Exclude
    private Set<Author> authors;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "songs")
    @ToString.Exclude
    private Set<Genre> genres;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Song song = (Song) o;
        return id != null && Objects.equals(id, song.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
