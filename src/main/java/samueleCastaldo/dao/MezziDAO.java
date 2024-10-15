package samueleCastaldo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import jakarta.persistence.Persistence;
import samueleCastaldo.entities.Mezzi;
import samueleCastaldo.exceptions.NotFoundException;


public class MezziDAO {
        private final EntityManager em;

    public MezziDAO(EntityManager em) {
        this.em = em;
    }

    public void saveMezzo(Mezzi mezzo) {
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

        }


