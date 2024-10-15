package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import samueleCastaldo.entities.Mezzi;
import samueleCastaldo.entities.Vidimato;

import java.time.LocalDate;
import java.util.List;

public class VidimatoDao {
    private final EntityManager em2;

    public VidimatoDao(EntityManager em2) {
        this.em2= em2;
    }

    public void save(Vidimato newVidimato) {
        EntityTransaction tx = em2.getTransaction();
        tx.begin();
        em2.persist(newVidimato);
        tx.commit();
        System.out.println("Vidimato creato: " + newVidimato + " saved");
    }

    public List<Vidimato> getBigliettiVidimatiByMezzo(long idMezzo) {
        TypedQuery<Vidimato> query = em2.createQuery("SELECT v FROM Vidimato v WHERE v.servizio.mezzo.id = :idMezzo" , Vidimato.class);
        query.setParameter("idMezzo", idMezzo);
        List<Vidimato> result = query.getResultList();
        for (Vidimato vid : result) {
            System.out.println(vid);
        }
        return result;
    }

    public List<Vidimato> getBigliettiVidimatiByPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Vidimato> query = em2.createQuery("SELECT v FROM Vidimato v WHERE v.data_convalida BETWEEN :dataInizio AND :dataFine" , Vidimato.class);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);
        List<Vidimato> result = query.getResultList();
        for (Vidimato vid : result) {
            System.out.println(vid);
        }
        return result;
    }



}
