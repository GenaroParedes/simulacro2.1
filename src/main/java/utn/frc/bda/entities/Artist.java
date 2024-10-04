package utn.frc.bda.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="artists")
public class Artist {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //@SequenceGenerator(name = "sqlite_sequence", sequenceName= "artists")
    private int id;
    private String name;

    @ManyToMany(mappedBy="artists")
    private List<Track> tracks;

    public Artist() {
    }

    public Artist(String name) {
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
        return "Artist{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return id == artist.id && Objects.equals(name, artist.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public void addTrack(Track track) {
        if(tracks == null) {
            tracks = new ArrayList<>();
        }
        tracks.add(track);
    }
}
