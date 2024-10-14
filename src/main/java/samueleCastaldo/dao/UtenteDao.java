package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import samueleCastaldo.entities.Utente;

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
        em2.close();
        System.out.println("L'utente con id " + utente.getId() + " è stato inserito");
    }
}
