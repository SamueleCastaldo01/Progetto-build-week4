package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import samueleCastaldo.entities.*;
import samueleCastaldo.exceptions.NotFoundException;

import java.util.List;

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
        System.out.println("Lo status con id " + status.getId() + " è stato inserito");
    }

    public Status findById(long id) {
        Status found = em2.find(Status.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public InServizio findInServizioById(long id) {
        InServizio found = em2.find(InServizio.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }


    public List<InServizio> getPeriodoDiServizioByMezzo (long idMezzo) {
        TypedQuery<InServizio> query = em2.createQuery("SELECT s FROM InServizio s WHERE s.mezzo.id = :idMezzo", InServizio.class);
        query.setParameter("idMezzo", idMezzo);
        List<InServizio> result = query.getResultList();
        for (InServizio r : result) {
            System.out.println(r);
        }
        return result;
    }

    public List<InManutenzione> getPeriodoDiManutenzioneByMezzo (long idMezzo) {
        TypedQuery<InManutenzione> query = em2.createQuery("SELECT s FROM InManutenzione s WHERE s.mezzo.id = :idMezzo", InManutenzione.class);
        query.setParameter("idMezzo", idMezzo);
        List<InManutenzione> result = query.getResultList();
        for (InManutenzione r : result) {
            System.out.println(r);
        }
        return result;
    }

    public List<Status> getPeriodoDiManutenzioneServizioByMezzo (long idMezzo) {
        TypedQuery<Status> query = em2.createQuery("SELECT s FROM Status s WHERE s.mezzo.id = :idMezzo", Status.class);
        query.setParameter("idMezzo", idMezzo);
        List<Status> result = query.getResultList();
        for (Status r : result) {
            System.out.println(r);
        }
        return result;
    }

    public void listaInServizioAttuali() {
        TypedQuery<InServizio> query = em2.createQuery("SELECT is FROM InServizio is WHERE is.dataFine IS NULL", InServizio.class);
        List<InServizio> result = query.getResultList();
        System.out.println("\nLista Mezzi attualmente in servizio");
        for(InServizio u : result) {
            System.out.println(u);
        }
    }

}
