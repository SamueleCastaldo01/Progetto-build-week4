package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import samueleCastaldo.entities.Abbonamento;
import samueleCastaldo.entities.Tessera;
import samueleCastaldo.entities.Utente;
import samueleCastaldo.exceptions.NotFoundException;

import java.util.List;

public class TesseraDao {
    private final EntityManager em2;

    public TesseraDao(EntityManager em2) {
        this.em2 = em2;
    }

    public void save(Tessera tessera) {
        EntityTransaction transiction = em2.getTransaction();

        transiction.begin();
        em2.persist(tessera);
        transiction.commit();
        System.out.println("La tessera con id " + tessera.getId() + " è stato inserito");
    }

    public Tessera findById(long id) {
        Tessera found = em2.find(Tessera.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void dettagliTessera(long idUtente) {
        TypedQuery<Tessera> query = em2.createQuery("SELECT t FROM Tessera t WHERE t.utente.id = :idUtente", Tessera.class);
        query.setParameter("idUtente", idUtente);
        Tessera result = query.getSingleResult();
        System.out.println("dettagli tessera: " + result);
    }


}
