package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import samueleCastaldo.entities.Tessera;
import samueleCastaldo.entities.Utente;
import samueleCastaldo.exceptions.NotFoundException;

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

    public Tessera findById(long id) {
        Tessera found = em2.find(Tessera.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }
}
