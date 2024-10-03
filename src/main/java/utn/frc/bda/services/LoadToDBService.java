package utn.frc.bda.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import utn.frc.bda.dbContext.DbContext;
import utn.frc.bda.entities.Track;

import java.util.List;

public class LoadToDBService {
    private final EntityManager em = DbContext.getInstance().getEntityManager();

    public void addAll(List<Track> tracks) {
        try {
            em.getTransaction().begin();
            tracks.forEach(em::persist);
            em.getTransaction().commit();
        }catch (Exception e) {
            System.out.println("No se pudo agregar todos los tracks" + e.getMessage());
            e.printStackTrace();
        }
    }
}
