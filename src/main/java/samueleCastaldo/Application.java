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
        StatusDao statDao = new StatusDao(em);
        VidimatoDao vidDao = new VidimatoDao(em);


        Utente u1 = new Utente("Aldo", "Baglio");
        Utente u2 = new Utente("Giovanni", "Neri");
        Utente u3 = new Utente("Giacomo", "Bianchi");
        Utente u4 = new Utente("Mario", "Rossi");

        Tessera t1 = new Tessera(LocalDate.now(), u1);
        Tessera t2 = new Tessera(LocalDate.now(), u3);

        Distributore dis1 = new Distributore(true);
        Distributore dis2 = new Distributore(true);
        Distributore dis3 = new Distributore(true);
        Rivenditore riv1 = new Rivenditore();
        Rivenditore riv2 = new Rivenditore();

        Biglietto big1 = new Biglietto(LocalDate.now(), dis1);
        Biglietto big2 = new Biglietto(LocalDate.now(), dis2);
        Biglietto big3 = new Biglietto(LocalDate.now(), dis1);
        Biglietto big4 = new Biglietto(LocalDate.now(), dis2);
        Biglietto big5 = new Biglietto(LocalDate.now(), dis3);
        Biglietto big6 = new Biglietto(LocalDate.now(), riv2);

        Abbonamento abb1 = new Abbonamento(LocalDate.now(), dis1, t1, TipoAbbonamento.SETTIMANALE);
        Abbonamento abb2 = new Abbonamento(LocalDate.now(), riv1, t1, TipoAbbonamento.MENSILE);
        Abbonamento abb3 = new Abbonamento(LocalDate.now(), dis2, t2, TipoAbbonamento.SETTIMANALE);
        Abbonamento abb4 = new Abbonamento(LocalDate.now(), riv1, t1, TipoAbbonamento.MENSILE);

        Mezzi a1 = new Autobus(50, "A9");
        Mezzi tram1 = new Tram(30, "T9");
        Mezzi a2 = new Autobus(50, "H9");
        Mezzi tram2 = new Tram(30, "B9");
        Mezzi a3 = new Autobus(50, "C9");
        Mezzi tram3 = new Tram(30, "D9");

        InManutenzione stat1 = new InManutenzione(LocalDate.of(2023, 01, 10), LocalDate.of(2024, 01, 10), a1);
        InServizio stat2 = new InServizio(LocalDate.of(2022, 01, 10), LocalDate.now(), tram1);
        InServizio stat6 = new InServizio(LocalDate.of(2024, 01, 10), LocalDate.now(), tram1);
        InServizio stat7 = new InServizio(LocalDate.of(2021, 01, 10), LocalDate.now(), tram1);
        InManutenzione stat8 = new InManutenzione(LocalDate.of(2002, 2, 2), LocalDate.of(2003, 2, 2), tram1);
        InServizio stat3 = new InServizio(LocalDate.of(2021, 01, 10), LocalDate.now(), tram2);
        InServizio stat4 = new InServizio(LocalDate.of(2021, 01, 10), LocalDate.now(), a2);
        InManutenzione stat5 = new InManutenzione(LocalDate.of(2024, 01, 11), LocalDate.now(), a1);

        Vidimato vidi1 = new Vidimato(big1, LocalDate.now(), stat2);
        Vidimato vidi2 = new Vidimato(big2, LocalDate.now(), stat2);
        Vidimato vidi3 = new Vidimato(big3, LocalDate.now(), stat2);
        Vidimato vidi4 = new Vidimato(big4, LocalDate.now(), stat3);
        Vidimato vidi5 = new Vidimato(big5, LocalDate.now(), stat4);
        Vidimato vidi6 = new Vidimato(big6, LocalDate.now(), stat4);


    /*
        utenteDao.save(u1);
        utenteDao.save(u2);
        utenteDao.save(u3);
        utenteDao.save(u4);

        tessDao.save(t1);
        tessDao.save(t2);

        emBigliettiDao.save(dis1);
        emBigliettiDao.save(dis2);
        emBigliettiDao.save(dis3);
        emBigliettiDao.save(riv1);
        emBigliettiDao.save(riv2);

        passDao.save(big1);
        passDao.save(big2);
        passDao.save(big3);
        passDao.save(big4);
        passDao.save(big5);
        passDao.save(big6);
        passDao.save(abb1);
        passDao.save(abb2);
        passDao.save(abb3);
        passDao.save(abb4);

        mezziDAO.save(a1);
        mezziDAO.save(a2);
        mezziDAO.save(a3);
        mezziDAO.save(tram1);
        mezziDAO.save(tram2);
        mezziDAO.save(tram3);
        
        statDao.save(stat1);
        statDao.save(stat2);
        statDao.save(stat3);
        statDao.save(stat4);
        statDao.save(stat5);
        statDao.save(stat6);
        statDao.save(stat7);
        statDao.save(stat8);

        vidDao.save(vidi1);
        vidDao.save(vidi2);
        vidDao.save(vidi3);
        vidDao.save(vidi4);
        vidDao.save(vidi5);
        vidDao.save(vidi6);
*/


        //prima query test
        //boolean checkabb1 = passDao.checkAbbByUtente(52, 1);
        // System.out.println(checkabb1);


        //Secondo query test, per raggruppamento
        System.out.println("\n-----------------------------");
        LocalDate dataInizio = LocalDate.of(2002, 3, 3);
        LocalDate dataFine = LocalDate.of(2025, 3, 3);
        List<Object[]> testQuery = passDao.numeroPassPerPeriodoEPuntoEmissione(dataInizio, dataFine);
        for (Object[] result : testQuery) {
            EmissioneBiglietti emissioneBiglietti = (EmissioneBiglietti) result[0];
            Long passCount = (Long) result[1];

            System.out.println("Punto emissione: " + emissioneBiglietti + ", Numero di biglietti e abbonamenti: " + passCount);
        }

        //terza query
        //statDao.getPeriodoDiServizioByMezzo(1005);
        //statDao.getPeriodoDiManutenzioneByMezzo(1005);
        //statDao.getPeriodoDiManutenzioneServizioByMezzo(1005);

        //quarta
        //vidDao.getBigliettiVidimatiByMezzo(1005);
        vidDao.getBigliettiVidimatiByPeriodo(LocalDate.of(2002, 10, 15), LocalDate.of(2025, 10, 10));


        emf.close();
        em.close();

    }

}
