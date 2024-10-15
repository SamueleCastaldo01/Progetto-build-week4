package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import samueleCastaldo.entities.Mezzi;
import samueleCastaldo.entities.Vidimato;

public class VidimatoDao {
    private final EntityManager em2;

    public VidimatoDao(EntityManager em2) {
        this.em2= em2;
    }

    public void save(Vidimato newVidimato) {
        EntityTransaction tx = em2.getTransaction();
        tx.begin();
        em2.persist(newVidimato);
        tx.commit();
        System.out.println("Vidimato creato: " + newVidimato + " saved");
    }

}
