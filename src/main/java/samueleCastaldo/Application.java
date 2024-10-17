package samueleCastaldo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import samueleCastaldo.dao.*;
import samueleCastaldo.entities.*;
import samueleCastaldo.exceptions.NotFoundException;

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
        MezziDAO mezziDAO = new MezziDAO(em);

        TesseraDao tessDao = new TesseraDao(em);
        UtenteDao utenteDao = new UtenteDao(em);
        PassDao passDao = new PassDao(em);
        EmBigliettiDao emBigliettiDao = new EmBigliettiDao(em);
        StatusDao statDao = new StatusDao(em);
        VidimatoDao vidDao = new VidimatoDao(em);
        TrattaDAO tratDAO = new TrattaDAO(em);
        ViaggioDAO viaggioDAO = new ViaggioDAO(em);
        Scanner scanner = new Scanner(System.in);


        System.out.println("Benvenuto nel sistema di gestione trasporti!");
        System.out.print("Vuoi popolare le Tabelle? (s/n): ");

        if (scanner.next().equalsIgnoreCase("s")) {
            PopolareTabelle popTabelle = new PopolareTabelle(emBigliettiDao, mezziDAO, passDao, statDao, tessDao, tratDAO, utenteDao, viaggioDAO, vidDao);
            popTabelle.addTabelle();
        }

        while (!exitProgram) {
            boolean accessGranted = false;

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
                    showUserMenu(scanner, em, utenteDao);
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
            System.out.println();
            System.out.println("Menu Amministratore:");
            System.out.println("1. Aggiungi un nuovo mezzo");
            System.out.println("2. Aggiungi tratta");
            System.out.println("3. Crea emissione Biglietto");
            System.out.println("4. Crea Viaggio");
            System.out.println("5. Controllo numero vendite biglietti");
            System.out.println("6. Storico mezzo servizio manutenzione by mezzo");
            System.out.println("7. Validazione match abbonamento utente");
            System.out.println("8. Biglietti vidimati tramite periodo di tempo");
            System.out.println("9. Calcola tempo medio di percorrenza");
            System.out.println("10. Contatore viaggio mezzo (by id mezzo)");
            System.out.println("11 Controllo vendite numero biglietti per emissione biglietti");

            System.out.println("0. Torna alla selezione utente");
            System.out.println("12. Esci dal programma");
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


    private static void showUserMenu(Scanner scanner, EntityManager em, UtenteDao utenteDao) {
        int choice = -1;
        boolean exitMenu = false;
        long indiceUtenteSelezionato;
        utenteDao.listaUtenti();
        System.out.print("Seleziona l'indice utente per loggarti: ");
        indiceUtenteSelezionato = scanner.nextLong();
        try {
            Utente loginUtente = utenteDao.LoginById(indiceUtenteSelezionato);
        }catch (NotFoundException ex){
            exitMenu = true;
            System.out.println(ex.getMessage());

        }

        while(!exitMenu) {
            scanner.nextLine();
            System.out.println();
            System.out.println("Menu Utente Comune:");
            System.out.println("1. Acquista un biglietto per viaggiare");
            System.out.println("2. Acquista un abbonamento (fornito di tessera)");
            System.out.println("3. Visualizzazione abbonamenti selezionabili");
            System.out.println("4. Visualizzazione dettagli tessera");
            System.out.println("5. Vidima biglietto");

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
        };
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
