package samueleCastaldo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import samueleCastaldo.dao.*;
import samueleCastaldo.entities.*;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("buildWeek4");
    EntityManager em = emf.createEntityManager();
    private static boolean exitProgram = false;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("buildWeek4");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        while (!exitProgram) {
            boolean accessGranted = false;

            System.out.println("Benvenuto nel sistema di gestione trasporti!");
            System.out.print("Vuoi popolare le Tabelle? (s/n): ");

            if (scanner.next().equalsIgnoreCase("s")) {
                popolatabelle(em);
            }

            while (!accessGranted && !exitProgram) {
                scanner.nextLine();
                System.out.print("Sei un amministratore o un utente comune? (admin/utente/exit): ");
                String userType = scanner.nextLine();

                if (userType.equalsIgnoreCase("admin")) {
                    accessGranted = adminAccess(scanner);
                    if (accessGranted) {
                        showAdminMenu(scanner, em);
                    } else {
                        System.out.println("Accesso amministratore negato. Riprova.");
                    }
                } else if (userType.equalsIgnoreCase("utente")) {
                    showUserMenu(scanner, em);
                } else if (userType.equalsIgnoreCase("exit")) {
                    exitProgram = true;
                    System.out.println("Chiusura del programma in corso...");
                } else {
                    System.out.println("Scelta non valida. Riprova.");
                }
            }
        }

        em.close();
        scanner.close();
        emf.close();
    }

    private static boolean adminAccess(Scanner scanner) {
        String adminPassword = "123";
        int attempts = 0;

        while (attempts < 3) {
            System.out.print("Inserisci la password per l'accesso come amministratore: ");
            String inputPassword = scanner.nextLine();

            if (inputPassword.equals(adminPassword)) {
                return true;
            } else {
                attempts++;
                System.out.println("Password errata. Hai " + (3 - attempts) + " tentativi rimanenti.");
            }
        }

        return false;
    }

    //  Amministratore
    private static void showAdminMenu(Scanner scanner, EntityManager em) {
        int choice = -1;

        do {
            System.out.println("Menu Amministratore:");
            System.out.println("1. Aggiungi un nuovo mezzo");
            System.out.println("2. Gestisci biglietti o abbonamenti");
            System.out.println("3. Calcola tempo medio di percorrenza");
            System.out.println("0. Torna alla selezione utente");
            System.out.println("9. Esci dal programma");
            System.out.print("Scegli un'opzione: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Errore: hai inserito un valore non valido. Per favore, inserisci un numero.");
                continue;
            }

            switch (choice) {
                case 1:
                    addMezzo(scanner, em);
                    break;
                case 2:
                    manageTicketsAndSubscriptions(scanner, em);
                    break;
                case 3:
                    // calculateAverageTravelTime(scanner, em); // Funzionalità da implementare
                    break;
                case 0:
                    return;
                case 9:
                    exitProgram = true;
                    System.out.println("Chiusura del programma in corso...");
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        } while (choice != 0 && !exitProgram);
    }

    // biglietti e abbonamenti
    private static void manageTicketsAndSubscriptions(Scanner scanner, EntityManager em) {
        boolean running = true;

        while (running && !exitProgram) {
            System.out.println("Gestisci Biglietti e Abbonamenti:");
            System.out.println("1. Visualizza informazioni sui biglietti");
            System.out.println("2. Visualizza informazioni sugli abbonamenti");
            System.out.println("3. Verifica validità abbonamento");
            System.out.println("0. Torna al menu amministratore");
            System.out.println("9. Esci dal programma");

            int scelta = -1;

            try {
                scelta = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Errore: hai inserito un valore non valido. Inserisci un numero tra 0 e 9.");
                continue;
            }

            switch (scelta) {
                case 1:
                    showTicketInfo(scanner, em);
                    break;
                case 2:
                    showSubscriptionInfo(scanner, em);
                    break;
                case 3:
                    verifySubscriptionValidity(scanner, em);
                    break;
                case 0:
                    return;
                case 9:
                    exitProgram = true;
                    System.out.println("Chiusura del programma in corso...");
                    return;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }


    private static void showUserMenu(Scanner scanner, EntityManager em) {
        int choice = -1;
        boolean exitMenu = false;

        do {
            System.out.println("Menu Utente Comune:");
            System.out.println("1. Acquista un biglietto per viaggiare");
            System.out.println("2. Acquista un abbonamento");
            System.out.println("0. Torna alla selezione utente");
            System.out.println("9. Esci dal programma");
            System.out.print("Scegli un'opzione: ");

            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Errore: hai inserito un valore non valido. Per favore, inserisci un numero.");
                continue;
            }

            switch (choice) {
                case 1:
                    buyTicket(scanner, em);
                    break;
                case 2:
                    buySubscription(scanner, em);
                    break;
                case 0:
                    exitMenu = true;
                    break;
                case 9:
                    System.out.println("Chiusura del programma in corso...");
                    System.exit(0);
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        } while (!exitMenu);
    }



    private static void showTicketInfo(Scanner scanner, EntityManager em) {
        // AldoRe salvami te

        System.out.println("Mostra informazioni sui biglietti non implementata.");
    }

    private static void showSubscriptionInfo(Scanner scanner, EntityManager em) {
        // AldoRe salvami te
        System.out.println("Mostra informazioni sugli abbonamenti non implementata.");
    }

    private static void verifySubscriptionValidity(Scanner scanner, EntityManager em) {
        System.out.print("Inserisci il numero della tessera da verificare: ");
        String numeroTessera = scanner.nextLine();

        // AldoRe salvami te
        System.out.println("Verifica della validità dell'abbonamento non implementata.");
    }

    private static void addMezzo(Scanner scanner, EntityManager em) {
        System.out.print("Inserisci la capienza del mezzo: ");
        int capienza = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Inserisci il codice del mezzo (es. 'c 18'): ");
        String codice = scanner.nextLine();

        System.out.print("Inserisci il tipo di mezzo (autobus/tram): ");
        String tipoMezzo = scanner.nextLine();

        Mezzi nuovoMezzo;
        if (tipoMezzo.equalsIgnoreCase("autobus")) {
            nuovoMezzo = new Autobus(capienza, codice);
        } else {
            nuovoMezzo = new Tram(capienza, codice);
        }

        // Persisto il nuovo mezzo nel database
        MezziDAO mezziDAO = new MezziDAO(em);
        mezziDAO.save(nuovoMezzo);

        System.out.println("Nuovo mezzo aggiunto con successo!");
    }



    private static void buyTicket(Scanner scanner, EntityManager em) {

        LocalDate dataEmissione = LocalDate.now();


        Biglietto nuovoBiglietto = new Biglietto(dataEmissione, null);

        em.getTransaction().begin();
        em.persist(nuovoBiglietto);
        em.getTransaction().commit();

        System.out.println("Biglietto acquistato con successo!");
    }
    private static void buySubscription(Scanner scanner, EntityManager em) {

        LocalDate dataEmissione = LocalDate.now();

        System.out.print("Inserisci il tipo di abbonamento (settimanale/mensile): ");
        String tipoAbbonamentoInput = scanner.nextLine();
        TipoAbbonamento tipoAbbonamento;


        if (tipoAbbonamentoInput.equalsIgnoreCase("settimanale")) {
            tipoAbbonamento = TipoAbbonamento.SETTIMANALE;
        } else if (tipoAbbonamentoInput.equalsIgnoreCase("mensile")) {
            tipoAbbonamento = TipoAbbonamento.MENSILE;
        } else {
            System.out.println("Tipo di abbonamento non valido. Riprova.");
            return;
        }


        System.out.print("Hai già una tessera? (s/n): ");
        String haTessera = scanner.nextLine();

        Tessera tessera;
        if (haTessera.equalsIgnoreCase("s")) {
            // Richiedere il numero della tessera
            System.out.print("Inserisci il numero della tessera: ");
            long numeroTessera = scanner.nextLong();
            tessera = em.find(Tessera.class, numeroTessera);
            if (tessera == null) {
                System.out.println("Tessera non trovata. Creazione nuova tessera.");
                tessera = new Tessera(); //  ID generato automaticamente
                em.getTransaction().begin();
                em.persist(tessera);
                em.getTransaction().commit();
                System.out.println("Nuova tessera creata con ID: " + tessera.getId());
            }
        } else {

            tessera = new Tessera(); // ID generato automaticamente
            em.getTransaction().begin();
            em.persist(tessera);
            em.getTransaction().commit();
            System.out.println("Nuova tessera creata con ID: " + tessera.getId());
        }


        Abbonamento nuovoAbbonamento = new Abbonamento(dataEmissione, null, tessera, tipoAbbonamento);
        em.getTransaction().begin();
        em.persist(nuovoAbbonamento);
        em.getTransaction().commit();

        System.out.println("Abbonamento acquistato con successo!");
    }


    public static void popolatabelle (EntityManager em) {

        MezziDAO mezziDAO = new MezziDAO(em);

        TesseraDao tessDao = new TesseraDao(em);
        UtenteDao utenteDao = new UtenteDao(em);
        PassDao passDao = new PassDao(em);
        EmBigliettiDao emBigliettiDao = new EmBigliettiDao(em);
        StatusDao statDao = new StatusDao(em);
        VidimatoDao vidDao = new VidimatoDao(em);
        TrattaDAO tratDAO = new TrattaDAO(em);
        ViaggioDAO viaggioDAO = new ViaggioDAO(em);

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
        InServizio stat2 = new InServizio(LocalDate.of(2022, 01, 10), LocalDate.now(), tram1, tratta1);
        InServizio stat6 = new InServizio(LocalDate.of(2024, 01, 10), LocalDate.now(), tram1, tratta4);
        InServizio stat7 = new InServizio(LocalDate.of(2021, 01, 10), LocalDate.now(), tram1, tratta1);
        InManutenzione stat8 = new InManutenzione(LocalDate.of(2002, 2, 2), LocalDate.of(2003, 2, 2), tram1);
        InServizio stat3 = new InServizio(LocalDate.of(2021, 01, 10), LocalDate.now(), tram2, tratta2);
        InServizio stat4 = new InServizio(LocalDate.of(2021, 01, 10), LocalDate.now(), a2, tratta6);
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








        //prima query test
        //boolean checkabb1 = passDao.checkAbbByUtente(52, 1);
        // System.out.println(checkabb1);


        //Secondo query test, per raggruppamento
       /* System.out.println("\n-----------------------------");
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

        //quinta query, sezione 3. Contatore c per n viaggi mezzo/tratta; input idMezzo
        long resultCountPT = viaggioDAO.countMezzoPercorreTratta(1505);

        //sesta query, lista di tempi effettivi da parte di un mezzo, by idMezzo
        List<Integer> resultTEffettiviMezzo = viaggioDAO.tempoEffettivoByIdMezzo(1505);

        //Settima query, mediaTempoEffettivoByMezzo
        double resultMediaTempoEffettivo = viaggioDAO.mediaTempoEffettivoByMezzo(1505);


        emf.close();
        em.close();

    }

}*/
