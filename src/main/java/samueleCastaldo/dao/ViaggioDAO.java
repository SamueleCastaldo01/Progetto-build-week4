package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import samueleCastaldo.entities.Biglietto;
import samueleCastaldo.entities.Viaggio;
import samueleCastaldo.exceptions.NotFoundException;

public class ViaggioDAO {
    private final EntityManager em2;

    public ViaggioDAO(EntityManager em2) {
        this.em2 = em2;
    }

    public void save(Viaggio viaggio) {
        EntityTransaction transiction = em2.getTransaction();

        transiction.begin();
        em2.persist(viaggio);
        transiction.commit();
        System.out.println("Il viaggio con id " + viaggio.getId() + " è stato inserito");
    }

    public Viaggio findById(long id) {
        Viaggio found = em2.find(Viaggio.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public long countMezzoPercorreTratta(long idMezzo) {
        TypedQuery<Long> query = em2.createQuery("SELECT COUNT(v) FROM Viaggio v WHERE v.servizio.mezzo.id = :idMezzo", Long.class);
        query.setParameter("idMezzo", idMezzo);
        long result = query.getSingleResult();
        System.out.println("conteggio: " + result);
        return result;
    }

}
