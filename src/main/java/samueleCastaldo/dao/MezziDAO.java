package samueleCastaldo.dao;

import jakarta.persistence.*;

import samueleCastaldo.entities.Mezzi;
import samueleCastaldo.exceptions.NotFoundException;

import java.util.List;


public class MezziDAO {
        private final EntityManager em;

    public MezziDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Mezzi mezzo) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(mezzo);
            tx.commit();
            System.out.println("Mezzo: " + mezzo + " saved");
    }
    public Mezzi getMezzo(long id) {
            Mezzi mezzotrovato = em.find(Mezzi.class, id);
            if (mezzotrovato == null) throw new NotFoundException(id);
            return mezzotrovato;
    }

    public List<Mezzi> listaMezzi() {
        TypedQuery<Mezzi> query = em.createQuery("SELECT m FROM Mezzi m", Mezzi.class);
        List<Mezzi> result = query.getResultList();
        System.out.println("\nLista mezzi");
        for (Mezzi m : result) {
            System.out.println(m);
        }
        return result;
    }

        }


