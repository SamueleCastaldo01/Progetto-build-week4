package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import samueleCastaldo.entities.EmissioneBiglietti;
import samueleCastaldo.entities.Pass;
import samueleCastaldo.exceptions.NotFoundException;

public class EmBigliettiDao {
    private final EntityManager em2;

    public EmBigliettiDao(EntityManager em2) {
        this.em2 = em2;
    }

    public void save(EmissioneBiglietti emBiglietti) {
        EntityTransaction transiction = em2.getTransaction();

        transiction.begin();
        em2.persist(emBiglietti);
        transiction.commit();
        System.out.println("Il distribitore con id " + emBiglietti.getId() + " è stato inserito");
    }

    public EmissioneBiglietti findById(long id) {
        EmissioneBiglietti found = em2.find(EmissioneBiglietti.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }
}
