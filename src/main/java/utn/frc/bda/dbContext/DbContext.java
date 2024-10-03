package utn.frc.bda.dbContext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DbContext {
    public static DbContext instance = null;
    private EntityManager em;

    public DbContext(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        this.em = emf.createEntityManager();
    }

    public static DbContext getInstance(){
        if(instance == null) {
            instance = new DbContext();
        }
        return instance;
    }

    public EntityManager getEntityManager(){
        return this.em;
    }

    public void closeEntityManager(){
        this.em.close();
    }
}
