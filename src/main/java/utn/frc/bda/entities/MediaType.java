package utn.frc.bda.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="mediaTypes")
public class MediaType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //@SequenceGenerator(name="sqlite_sequence", sequenceName="mediaTypes")
    private int id;
    private String name;

    public MediaType() {
    }

    public MediaType(String name) {
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

    @Override
    public String toString() {
        return "MediaType{" +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaType mediaType = (MediaType) o;
        return id == mediaType.id && Objects.equals(name, mediaType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}