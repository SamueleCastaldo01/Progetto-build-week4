package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import samueleCastaldo.entities.EmissioneBiglietti;
import samueleCastaldo.entities.Status;
import samueleCastaldo.exceptions.NotFoundException;

public class StatusDao {
    private final EntityManager em2;

    public StatusDao(EntityManager em2) {
        this.em2 = em2;
    }

    public void save(Status status) {
        EntityTransaction transiction = em2.getTransaction();

        transiction.begin();
        em2.persist(status);
        transiction.commit();
        System.out.println("Il distribitore con id " + status.getId() + " è stato inserito");
    }

    public Status findById(long id) {
        Status found = em2.find(Status.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }
}
