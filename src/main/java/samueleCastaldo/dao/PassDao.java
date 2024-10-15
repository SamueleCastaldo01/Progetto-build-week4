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

    public boolean checkAbbByUtente(long idUtente, long idAbbonamento) {
        TypedQuery <Long> query1 = em2.createQuery
                ("SELECT t.id FROM Tessera t WHERE t.utente_id = :idU",Long.class);
        query1.setParameter("idU",idUtente);
        TypedQuery <Long> query2 = em2.createQuery
                ("SELECT a.id_tessera FROM Abbonamento a WHERE a.id = :idA",Long.class);
        query2.setParameter("idA",idAbbonamento);
        if (query1 == query2) {
            return true;
        }
        return false;
    }
}
