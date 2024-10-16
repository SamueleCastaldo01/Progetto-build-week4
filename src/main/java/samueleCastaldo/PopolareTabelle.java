package samueleCastaldo;

import jakarta.persistence.EntityManager;
import samueleCastaldo.dao.*;
import samueleCastaldo.entities.*;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PopolareTabelle {
    private MezziDAO mezziDAO ;
    private TesseraDao tessDao;
    private UtenteDao utenteDao ;
    private PassDao passDao ;
    private EmBigliettiDao emBigliettiDao;
    private StatusDao statDao;
    private VidimatoDao vidDao;
    private TrattaDAO tratDAO;
    private  ViaggioDAO viaggioDAO;

    public PopolareTabelle(EmBigliettiDao emBigliettiDao, MezziDAO mezziDAO, PassDao passDao, StatusDao statDao, TesseraDao tessDao, TrattaDAO tratDAO, UtenteDao utenteDao, ViaggioDAO viaggioDAO, VidimatoDao vidDao) {
        this.emBigliettiDao = emBigliettiDao;
        this.mezziDAO = mezziDAO;
        this.passDao = passDao;
        this.statDao = statDao;
        this.tessDao = tessDao;
        this.tratDAO = tratDAO;
        this.utenteDao = utenteDao;
        this.viaggioDAO = viaggioDAO;
        this.vidDao = vidDao;
    }

    public void addTabelle () {
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

        Tratte tratta1 = new Tratte("Napoli", "Roma", 360);
        Tratte tratta2 = new Tratte("Milano", "Torino", 220);
        Tratte tratta3 = new Tratte("Parma", "Pisa", 180);
        Tratte tratta4 = new Tratte("Roma", "Napoli", 360);
        Tratte tratta5 = new Tratte("Torino", "Venezia", 100);
        Tratte tratta6 = new Tratte("Torino", "Milano", 220);
        Tratte tratta7 = new Tratte("Pisa", "Parma", 180);
        Tratte tratta8 = new Tratte("Bergamo", "Roma", 360);


        InManutenzione stat1 = new InManutenzione(LocalDate.of(2023, 01, 10), LocalDate.of(2024, 01, 10), a1);
        InServizio stat2 = new InServizio(null, LocalDate.now(), tram1, tratta1);
        InServizio stat6 = new InServizio(null, LocalDate.now(), tram1, tratta4);
        InServizio stat7 = new InServizio(LocalDate.of(2021, 01, 10), LocalDate.now(), tram1, tratta1);
        InManutenzione stat8 = new InManutenzione(LocalDate.of(2002, 2, 2), LocalDate.of(2003, 2, 2), tram1);
        InServizio stat3 = new InServizio(LocalDate.of(2021, 01, 10), LocalDate.now(), tram2, tratta2);
        InServizio stat4 = new InServizio(null, LocalDate.now(), a2, tratta6);
        InManutenzione stat5 = new InManutenzione(LocalDate.of(2024, 01, 11), LocalDate.now(), a1);

        Vidimato vidi1 = new Vidimato(big1, LocalDate.now(), stat2);
        Vidimato vidi2 = new Vidimato(big2, LocalDate.now(), stat2);
        Vidimato vidi3 = new Vidimato(big3, LocalDate.now(), stat2);
        Vidimato vidi4 = new Vidimato(big4, LocalDate.now(), stat3);
        Vidimato vidi5 = new Vidimato(big5, LocalDate.now(), stat4);
        Vidimato vidi6 = new Vidimato(big6, LocalDate.now(), stat4);

        Viaggio viaggio1 = new Viaggio(120, LocalDateTime.of(2024, 10, 15, 15, 10), stat2 );
        Viaggio viaggio2 = new Viaggio(90, LocalDateTime.of(2023, 12, 5, 18, 20), stat6 );
        Viaggio viaggio3 = new Viaggio(120, LocalDateTime.of(2020, 10, 12, 5, 50), stat2 );
        Viaggio viaggio4 = new Viaggio(120, LocalDateTime.of(2022, 10, 25, 7, 19), stat7 );
        Viaggio viaggio5 = new Viaggio(120, LocalDateTime.of(2024, 10, 30, 16, 14), stat2 );
        Viaggio viaggio6 = new Viaggio(120, LocalDateTime.of(2019, 10, 20, 20, 14), stat2 );
        Viaggio viaggio7 = new Viaggio(120, LocalDateTime.of(2024, 10, 18, 12, 40), stat4 );
        Viaggio viaggio8 = new Viaggio(120, LocalDateTime.of(2024, 10, 10, 15, 30), stat2 );
        Viaggio viaggio9 = new Viaggio(120, LocalDateTime.of(2024, 10, 31, 15, 20), stat6 );
        Viaggio viaggio10 = new Viaggio(120, LocalDateTime.of(2014, 10, 15, 15, 25), stat6 );
        Viaggio viaggio11 = new Viaggio(120, LocalDateTime.of(2023, 10, 15, 15, 17), stat2 );


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

        tratDAO.save(tratta1);
        tratDAO.save(tratta2);
        tratDAO.save(tratta3);
        tratDAO.save(tratta4);
        tratDAO.save(tratta5);
        tratDAO.save(tratta6);
        tratDAO.save(tratta7);
        tratDAO.save(tratta8);

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

        viaggioDAO.save(viaggio1);
        viaggioDAO.save(viaggio2);
        viaggioDAO.save(viaggio3);
        viaggioDAO.save(viaggio4);
        viaggioDAO.save(viaggio5);
        viaggioDAO.save(viaggio6);
        viaggioDAO.save(viaggio6);
        viaggioDAO.save(viaggio7);
        viaggioDAO.save(viaggio8);
        viaggioDAO.save(viaggio9);
        viaggioDAO.save(viaggio10);
        viaggioDAO.save(viaggio11);

    }
}
