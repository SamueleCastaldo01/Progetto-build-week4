package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import samueleCastaldo.entities.Tessera;

public class TesseraDao {
    private final EntityManager em2;

    public TesseraDao(EntityManager em2) {
        this.em2 = em2;
    }

    public void save(Tessera tessera) {
        EntityTransaction transiction = em2.getTransaction();

        transiction.begin();
        em2.persist(tessera);
        transiction.commit();
        em2.close();
        System.out.println("La tessera con id " + tessera.getId() + " è stato inserito");
    }
}
