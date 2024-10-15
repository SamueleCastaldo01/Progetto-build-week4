package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import samueleCastaldo.entities.Abbonamento;
import samueleCastaldo.entities.Pass;
import samueleCastaldo.entities.Tessera;
import samueleCastaldo.entities.Utente;
import samueleCastaldo.exceptions.NotFoundException;

import java.util.List;


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

    public Abbonamento findAbbById(long id) {
        Abbonamento found = em2.find(Abbonamento.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public boolean checkAbbByUtente(Utente utente, Abbonamento abbonamento) {
        TypedQuery<Long> query1 = em2.createQuery(
                "SELECT t.id FROM Tessera t WHERE t.utente = :utente", Long.class);
        query1.setParameter("utente", utente);

        Long tesseraId1 = query1.getSingleResult();
        System.out.println("query 1 result: " + tesseraId1);

        TypedQuery<Long> query2 = em2.createQuery(
                "SELECT a.tessera.id FROM Abbonamento a WHERE a = :abbonamento", Long.class);
        query2.setParameter("abbonamento", abbonamento);

        Long tesseraId2 = query2.getSingleResult();
        System.out.println("query 2 result: " + tesseraId2);

        if(tesseraId1.equals(tesseraId2)) {
            return true;
        }
        return false;
    }
}
