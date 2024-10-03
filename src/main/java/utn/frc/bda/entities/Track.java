package utn.frc.bda.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="tracks")
public class Track {
    @Id
    @Column(name="track_id")
    private int trackId;
    private String name;
    private int miliseconds;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="genre_id", referencedColumnName="id")
    private Genre genre;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="media_type_id", referencedColumnName="id")
    private MediaType mediaType;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="album_id", referencedColumnName="id")
    private Album album;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "track_artist",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private List<Artist> artists;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "track_composer",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "composer_id")
    )
    private List<Composer> composers;

    public Track() {
    }

    public Track(int trackId, String name, int miliseconds, Genre genre, MediaType mediaType, Album album, List<Artist> artists, List<Composer> composers) {
        this.trackId = trackId;
        this.name = name;
        this.miliseconds = miliseconds;
        this.genre = genre;
        this.mediaType = mediaType;
        this.album = album;
        this.artists = artists;
        this.composers = composers;
        album.addTrack(this);
        this.artists.forEach(artist -> artist.addTrack(this));
        this.composers.forEach(composer -> composer.addTrack(this));
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMiliseconds() {
        return miliseconds;
    }

    public void setMiliseconds(int miliseconds) {
        this.miliseconds = miliseconds;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Composer> getComposers() {
        return composers;
    }

    public void setComposers(List<Composer> composers) {
        this.composers = composers;
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackId=" + trackId +
                ", name='" + name + '\'' +
                ", miliseconds=" + miliseconds +
                ", genre=" + genre.toString() +
                ", mediaType=" + mediaType.toString() +
                ", album=" + album.toString() +
                ", artists=" + artists.toString() +
                ", composers=" + composers.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return trackId == track.trackId && Objects.equals(name, track.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackId, name);
    }
}
