package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import samueleCastaldo.entities.Biglietto;
import samueleCastaldo.entities.Viaggio;
import samueleCastaldo.exceptions.NotFoundException;

import java.util.List;

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
        TypedQuery<Long> query = em2.createQuery("SELECT COUNT(v) FROM Viaggio v WHERE v.mezzo.id = :idMezzo", Long.class);
        query.setParameter("idMezzo", idMezzo);
        long result = query.getSingleResult();
        System.out.println("conteggio: " + result);
        return result;
    }

    public List<Integer> tempoEffettivoByIdMezzo(long idMezzo) {
        TypedQuery<Integer> query = em2.createQuery("SELECT v.tempo_effettivo FROM Viaggio v WHERE v.mezzo.id = :idMezzo", Integer.class);
        query.setParameter("idMezzo", idMezzo);
        List<Integer> result = query.getResultList();
        for (Integer r : result) {
            System.out.println("Tempo Effettivo: " + r + " minuti");
        }
        return result;
    }

    public double mediaTempoEffettivoByMezzo(long idMezzo) {
        TypedQuery<Double> query = em2.createQuery("SELECT AVG(v.tempo_effettivo)  FROM Viaggio v WHERE v.mezzo.id = :idMezzo", Double.class);
        query.setParameter("idMezzo", idMezzo);
        double result = query.getSingleResult();
        System.out.println("La media dei viaggi: " +result + " min");
        return result;
    }

}
