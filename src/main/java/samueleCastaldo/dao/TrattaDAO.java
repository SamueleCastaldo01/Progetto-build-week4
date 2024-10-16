package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import samueleCastaldo.entities.Tratte;
import samueleCastaldo.exceptions.NotFoundException;

public class TrattaDAO {
    private final EntityManager em2;

    public TrattaDAO(EntityManager em2) {
        this.em2 = em2;
    }

    public void save(Tratte tratta) {
        EntityTransaction transaction = em2.getTransaction();

        transaction.begin();
        em2.persist(tratta);
        transaction.commit();
        System.out.println("La tratta con id" + tratta.getId() + "è stata inserita");
    }

    public Tratte findById(long id) {
        Tratte found = em2.find(Tratte.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }
}
