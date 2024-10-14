package samueleCastaldo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import samueleCastaldo.dao.EmBigliettiDao;
import samueleCastaldo.dao.PassDao;
import samueleCastaldo.dao.TesseraDao;
import samueleCastaldo.dao.UtenteDao;
import samueleCastaldo.entities.*;

import java.time.LocalDate;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("buildWeek4");

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();
        System.out.println("Hello World!");

        TesseraDao tessDao = new TesseraDao(em);
        UtenteDao utenteDao = new UtenteDao(em);
        PassDao passDao = new PassDao(em);
        EmBigliettiDao emBigliettiDao = new EmBigliettiDao(em);


        Utente u1 = new Utente("Giovanni", "Muchaccia");
//        utenteDao.save(u1);

        Utente utFound = utenteDao.findById(1);

        Tessera t1 = new Tessera(LocalDate.now(), utFound);
        //tessDao.save(t1);

        Distributore dis1 = new Distributore(true);
        Distributore dis2 = new Distributore(true);
        Rivenditore riv1 = new Rivenditore();

        //emBigliettiDao.save(riv1);

        Biglietto big1 = new Biglietto(LocalDate.now());
        Tessera tessFound = tessDao.findById(52);
        Abbonamento abb1 = new Abbonamento(LocalDate.now(),TipoAbbonamento.SETTIMANALE,tessFound);
        //passDao.save(abb1);

    }

}
