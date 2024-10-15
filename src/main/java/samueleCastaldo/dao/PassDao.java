package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import samueleCastaldo.entities.Abbonamento;
import samueleCastaldo.entities.Pass;
import samueleCastaldo.entities.Tessera;
import samueleCastaldo.entities.Utente;
import samueleCastaldo.exceptions.NotFoundException;

import java.time.LocalDate;
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

    //numero biglietti o abbonamenti, emessi in un dato periodo di tempo, e per punto di emissione
    public List<Object[]> numeroPassPerPeriodoEPuntoEmissione(LocalDate data_inizio, LocalDate data_fine) {
        TypedQuery<Object[]> query = em2.createQuery
                ("SELECT p.emissioneBiglietti, COUNT(p) FROM Pass p WHERE p.dataEmissione BETWEEN :dataInizio AND :dataFine GROUP BY p.emissioneBiglietti", Object[].class);
        query.setParameter("dataInizio", data_inizio);
        query.setParameter("dataFine", data_fine);
        return query.getResultList();
    }

    //validazione tramite idUtente e idAbbonamento
    public boolean checkAbbByUtente(long idUtente, long idAbbonamento) {
        TypedQuery<Long> query1 = em2.createQuery(
                "SELECT t.id FROM Tessera t WHERE t.utente.id = :idu", Long.class);
        query1.setParameter("idu", idUtente);

        Long tesseraId1 = query1.getSingleResult();
        System.out.println("query 1 result: " + tesseraId1);

        TypedQuery<Long> query2 = em2.createQuery(
                "SELECT a.tessera.id FROM Abbonamento a WHERE a.id = :ida", Long.class);
        query2.setParameter("ida", idAbbonamento);

        Long tesseraId2 = query2.getSingleResult();
        System.out.println("query 2 result: " + tesseraId2);

        if (tesseraId1.equals(tesseraId2)) {
            return true;
        }
        return false;
    }
}
