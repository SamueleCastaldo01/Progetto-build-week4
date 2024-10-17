package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import samueleCastaldo.entities.*;
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
    }

    public Pass findById(long id) {
        Pass found = em2.find(Pass.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public Biglietto findByIdBiglietto(long id) {
        Biglietto found = em2.find(Biglietto.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }


    public void deleteBigliettoById (long idBiglietto){
        EntityTransaction transaction = em2.getTransaction();
        try {
            transaction.begin();
            Biglietto bigliettofound = findByIdBiglietto(idBiglietto);
            if (bigliettofound != null){
                em2.remove(bigliettofound);
                transaction.commit();
                System.out.println("Il biglietto con id "+ idBiglietto+ " è stato eliminato");
            } else {
                System.out.println("Nessun biglietto con id "+ idBiglietto);
            }
        } catch (IllegalArgumentException ex){
            System.out.println("Devi inserire un numero");
        }
    }

    public void deleteBiglietto (Biglietto biglietto){
        EntityTransaction transaction = em2.getTransaction();
        try {
            transaction.begin();
            if (biglietto != null){
                em2.remove(biglietto);
                transaction.commit();
                System.out.println("Il biglietto con id "+ biglietto.getId()+ " è stato eliminato");
            } else {
                System.out.println("Nessun biglietto con id "+ biglietto.getId());
            }
        } catch (IllegalArgumentException ex){
            System.out.println("Devi inserire un numero");
        }
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

    public void listaAbbonamenti(long idUtente) {
        TypedQuery<Abbonamento> query = em2.createQuery("SELECT e FROM Abbonamento e WHERE e.tessera.utente.id = :idUtente", Abbonamento.class);
        query.setParameter("idUtente", idUtente);
        List<Abbonamento> result = query.getResultList();
        System.out.println("\nLista Abbonamenti per utente selezionato");
        for(Abbonamento u : result) {
            System.out.println(u);
        }
    }

    public Abbonamento dettagliAbbonamento(long id) {
        Abbonamento found = em2.find(Abbonamento.class, id);
        if (found == null) throw new NotFoundException(id);
        System.out.println("\nDettagli Abbonamento: " +found);
        return found;
    }
}
