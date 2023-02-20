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
@Table(name = "albums")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "album_id", nullable = false)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "album")
    @ToString.Exclude
    private Set<Song> songs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Album album = (Album) o;
        return id != null && Objects.equals(id, album.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
