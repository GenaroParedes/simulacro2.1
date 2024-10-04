package utn.frc.bda.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name = "sqlite_sequence", sequenceName= "albums")
    private int id;
    private String name;
    @Column(name= "total_miliseconds")
    private int totalMiliseconds;

    @OneToMany(mappedBy="album")
    private List<Track> tracks;

    public Album() {
    }

    public Album(String name, int totalMiliseconds) {
        this.name = name;
        this.totalMiliseconds = totalMiliseconds;
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

    public int getTotalMiliseconds() {
        return totalMiliseconds;
    }

    public void setTotalMiliseconds(int totalMiliseconds) {
        this.totalMiliseconds = totalMiliseconds;
    }

    @Override
    public String toString() {
        return "Album{" +
                ", name='" + name + '\'' +
                ", totalMiliseconds=" + totalMiliseconds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return id == album.id && Objects.equals(name, album.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public void addTrack(Track track) {
        if (this.tracks == null) {
            this.tracks = new ArrayList<>();
        }
        this.tracks.add(track);
    }

    public String getDuracionTotal() {
        int totalMiliseconds = getTotalMiliseconds();
        int horas = totalMiliseconds / 3600000;
        int minutos = (totalMiliseconds % 3600000) / 60000;
        int segundos = (totalMiliseconds % 60000) / 1000;
        int miliseconds = totalMiliseconds % 1000;
        return String.format("%dhs : %dmin : %dseg : %dms", horas, minutos, segundos, miliseconds);
    }
}
