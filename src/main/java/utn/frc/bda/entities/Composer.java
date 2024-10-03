package utn.frc.bda.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="composers")
public class Composer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name="sqlite_sequence", sequenceName="composers")
    private int id;
    private String name;

    @ManyToMany(mappedBy="composers")
    private List<Track> tracks;

    public Composer() {
    }

    public Composer(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "Composer{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Composer composer = (Composer) o;
        return id == composer.id && Objects.equals(name, composer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public void addTrack(Track track) {
        if (tracks == null ) {
            tracks = new ArrayList<>();
        }
        tracks.add(track);
    }
}
