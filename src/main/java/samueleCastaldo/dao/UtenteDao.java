package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import samueleCastaldo.entities.Utente;
import samueleCastaldo.exceptions.NotFoundException;

import javax.swing.text.html.parser.Entity;

public class UtenteDao {
    private final EntityManager em2;

    public UtenteDao(EntityManager em2) {
        this.em2 = em2;
    }

    public void save(Utente utente) {
        EntityTransaction transiction = em2.getTransaction();

        transiction.begin();
        em2.persist(utente);
        transiction.commit();
        System.out.println("L'utente con id " + utente.getId() + " è stato inserito");
    }

    public Utente findById(long id) {
        Utente found = em2.find(Utente.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }
}
