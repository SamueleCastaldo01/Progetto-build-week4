package samueleCastaldo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import samueleCastaldo.dao.PassDao;
import samueleCastaldo.dao.TesseraDao;
import samueleCastaldo.dao.UtenteDao;
import samueleCastaldo.entities.Biglietto;
import samueleCastaldo.entities.Tessera;
import samueleCastaldo.entities.Utente;

import java.time.LocalDate;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("buildWeek4");
    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();
        System.out.println("Hello World!");

        TesseraDao tessDao = new TesseraDao(em);
        UtenteDao utenteDao = new UtenteDao(em);
        PassDao passDao = new PassDao(em);


        Utente u1 = new Utente("Giovanni","Muchaccia");
//        utenteDao.save(u1);

        Tessera t1 = new Tessera(LocalDate.now(),u1);
        tessDao.save(t1);
    }

}
