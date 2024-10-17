package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import samueleCastaldo.entities.Utente;
import samueleCastaldo.exceptions.NotFoundException;

import javax.swing.text.html.parser.Entity;
import java.util.List;

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

    public void listaUtenti() {
        TypedQuery<Utente> query = em2.createQuery("SELECT u FROM Utente u", Utente.class);
        List<Utente> result = query.getResultList();
        System.out.println("\nLista utenti");
        for(Utente u : result) {
            System.out.println(u);
        }
    }

    public Utente LoginById(long id) {
        Utente found = em2.find(Utente.class, id);
        if (found == null) throw new NotFoundException(id);
        System.out.println("\nTi sei loggato come: " +found);
        return found;
    }

}
