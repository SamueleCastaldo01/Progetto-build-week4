package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import samueleCastaldo.entities.Pass;
import samueleCastaldo.entities.Tessera;
import samueleCastaldo.exceptions.NotFoundException;


public class PassDao {
    private final EntityManager em2;

    public PassDao(EntityManager em2) {
        this.em2 = em2;
    }

    public void save(Pass pass) {
        EntityTransaction transiction = em2.getTransaction();

        transiction.begin();
        em2.persist(pass);
        transiction.commit();
        em2.close();
        System.out.println("Il pass con id " + pass.getId() + " è stato inserito");
    }

    public Pass findById(long id) {
        Pass found = em2.find(Pass.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }
}
