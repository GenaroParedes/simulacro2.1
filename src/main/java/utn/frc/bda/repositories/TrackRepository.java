package utn.frc.bda.repositories;

import jakarta.persistence.EntityManager;
import utn.frc.bda.dbContext.DbContext;

public class TrackRepository {
    private final EntityManager em = DbContext.getInstance().getEntityManager();

    public TrackRepository(){}


}
