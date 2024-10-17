package samueleCastaldo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import samueleCastaldo.dao.*;
import samueleCastaldo.entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
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
        TrattaDAO tratDAO = new TrattaDAO(em);
        ViaggioDAO viaggioDAO = new ViaggioDAO(em);

        PopolareTabbele popTabelle = new PopolareTabbele(emBigliettiDao, mezziDAO, passDao, statDao, tessDao, tratDAO, utenteDao, viaggioDAO, vidDao);
        popTabelle.addTabelle();


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
        //vidDao.getBigliettiVidimatiByPeriodo(LocalDate.of(2002, 10, 15), LocalDate.of(2025, 10, 10));

        //quinta query, sezione 3. Contatore c per n viaggi mezzo/tratta; input idMezzo
        //long resultCountPT = viaggioDAO.countMezzoPercorreTratta(1505);

        //sesta query, lista di tempi effettivi da parte di un mezzo, by idMezzo
        //List<Integer> resultTEffettiviMezzo = viaggioDAO.tempoEffettivoByIdMezzo(1505);

        //Settima query, mediaTempoEffettivoByMezzo
        //double resultMediaTempoEffettivo = viaggioDAO.mediaTempoEffettivoByMezzo(1505);


        emf.close();
        em.close();

    }

}
