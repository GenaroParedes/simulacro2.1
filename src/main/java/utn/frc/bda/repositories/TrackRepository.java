package utn.frc.bda.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import utn.frc.bda.dbContext.DbContext;
import utn.frc.bda.entities.Track;

import java.util.List;

public class TrackRepository {
    private final EntityManager em = DbContext.getInstance().getEntityManager();

    public TrackRepository(){

    }

    public List<Track> findAll(){
        String jpql = "SELECT t FROM Track t";
        TypedQuery<Track> query = em.createQuery(jpql, Track.class);
        return query.getResultList();
    }

    public Track findById(int id){
        try {
            String jpql = "SELECT t FROM Track t WHERE t.id = :id";
            TypedQuery<Track> query = em.createQuery(jpql, Track.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se puede encontrar el objeto");
        }
    }


    public List<Track> findByAlbum(String nameTrack) {
        try {
            String jpql = "SELECT t FROM Track t WHERE t.album.name = :nameTrack";
            TypedQuery<Track> query = em.createQuery(jpql, Track.class);
            query.setParameter("nameTrack", nameTrack);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se encontraron resultados");
        }
    }

    public List<Track> findByGenre(String nameGenre) {
        try {
            String jpql = "SELECT t FROM Track t WHERE t.genre.name = :nameGenre";
            TypedQuery<Track> query = em.createQuery(jpql, Track.class);
            query.setParameter("nameGenre", nameGenre);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se encontraron resultados");
        }
    }

    public List<Track> findByArtist(String nameArtist) {
        try {
            String jpql = "SELECT t FROM Track t, IN(t.artists) ta WHERE ta.name = :nameArtist";
            TypedQuery<Track> query = em.createQuery(jpql, Track.class);
            query.setParameter("nameArtist", nameArtist);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se encontraron resultados");
        }
    }
}
