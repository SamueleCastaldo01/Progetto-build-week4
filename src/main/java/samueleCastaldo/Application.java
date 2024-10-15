package samueleCastaldo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import samueleCastaldo.dao.*;
import samueleCastaldo.entities.*;
import samueleCastaldo.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("buildWeek4");

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();
        System.out.println("Hello World!");
        MezziDAO mezziDAO = new MezziDAO(em);
        TesseraDao tessDao = new TesseraDao(em);
        UtenteDao utenteDao = new UtenteDao(em);
        PassDao passDao = new PassDao(em);
        EmBigliettiDao emBigliettiDao = new EmBigliettiDao(em);



        Utente u1 = new Utente("Aldo", "Baglio");
        //utenteDao.save(u1);

        Utente utFound = utenteDao.findById(52);

        Tessera t1 = new Tessera(LocalDate.now(), utFound);
        //tessDao.save(t1);

        Distributore dis1 = new Distributore(true);
        Distributore dis2 = new Distributore(true);
        Rivenditore riv1 = new Rivenditore();

        //emBigliettiDao.save(dis2);
        //emBigliettiDao.save(riv1);

       EmissioneBiglietti emBiglFound = emBigliettiDao.findById(1);

        Tessera tessFound = tessDao.findById(1);
        Biglietto big1 = new Biglietto(LocalDate.now(), emBiglFound);
        Abbonamento abb1 = new Abbonamento(LocalDate.now(),emBiglFound,tessFound,TipoAbbonamento.SETTIMANALE);
        //passDao.save(big1);


        //prima query test
        //boolean checkabb1 = passDao.checkAbbByUtente(52, 1);
       // System.out.println(checkabb1);


        Mezzi m2 = new Autobus(50, "Manutenzione","A9");
        //mezziDAO.saveMezzo(m2);




        //Secondo query test, per raggruppamento
        System.out.println("\n-----------------------------");
        LocalDate dataInizio = LocalDate.of(2002, 3,3);
        LocalDate dataFine = LocalDate.of(2025, 3, 3);
        List<Object[]> testQuery = passDao.numeroPassPerPeriodoEPuntoEmissione(dataInizio, dataFine);
        for (Object[] result : testQuery) {
            EmissioneBiglietti emissioneBiglietti = (EmissioneBiglietti) result[0];
            Long passCount = (Long) result[1];

            System.out.println("Punto emissione: " + emissioneBiglietti + ", Numero di biglietti e abbonamenti: " + passCount);
        }

        emf.close();
        em.close();

    }

}
