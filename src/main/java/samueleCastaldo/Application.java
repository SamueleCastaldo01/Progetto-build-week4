package samueleCastaldo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import samueleCastaldo.dao.*;
import samueleCastaldo.entities.*;
import samueleCastaldo.exceptions.NotFoundException;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            System.out.println("5. Storico mezzo servizio manutenzione by mezzo");
            System.out.println("6. Validazione match abbonamento utente");
            System.out.println("7. Biglietti vidimati tramite periodo di tempo");
            System.out.println("8. Calcola tempo medio di percorrenza");
            System.out.println("9. Contatore viaggio mezzo (by id mezzo)");
            System.out.println("10. Controllo vendite numero biglietti per emissione biglietti");

            System.out.println("0. Torna alla selezione utente");
            System.out.println("11. Esci dal programma");
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
                    addTratta(scanner, em);
                    break;
                case 3:
                    addEmBiglietto(scanner, em);
                    break;
                case 4:
                    addViaggio(scanner, em);
                    break;
                case 5:
                    storicoStatusMezzo(scanner, em);
                    break;
                case 6:
                    validazioneUtenteAbb(scanner, em);
                    break;
                case 7:
                    countBigliettiVidimatiByPeriod(scanner,em);
                    break;
                case 8:
                    tempoMedioDiPercorrezaMezzo(scanner,em);
                    break;
                case 9:
                    countViaggioByMezzo(scanner,em);
                    break;
                case 10:
                    checkVenditeBiglietto(scanner, em);
                    break;

                case 0:
                    return;
                case 11:
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
        } catch (NotFoundException ex) {
            exitMenu = true;
            System.out.println(ex.getMessage());
        }

        while (!exitMenu) {
            scanner.nextLine();
            System.out.println();
            System.out.println("Menu Utente:");
            System.out.println("1. Acquista un biglietto per viaggiare");
            System.out.println("//2. Acquista un abbonamento (fornito di tessera)");
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
                    compraBiglietto(scanner, em);
                    break;
                case 2:
                    buySubscription(scanner, em);
                    break;
                case 3:
                    visualizzaAbbonamenti(scanner, em, indiceUtenteSelezionato);
                    break;
                case 4:
                    dettagliTessera(scanner, em, indiceUtenteSelezionato);
                    break;
                case 5:
                    vidimaBiglietto(scanner, em);
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
        }
        ;
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

    private static void addTratta(Scanner scanner, EntityManager em) {
        System.out.print("Inserisci la zona di partenza: ");
        String zonaPartenza = scanner.nextLine();

        System.out.print("Inserisci zona arrivo: ");
        String zonaArrivo = scanner.nextLine();

        System.out.print("Inserisci il tempo previsto (in minuti): ");
        int tempoPrevisto = Integer.parseInt(scanner.nextLine());

        Tratte userTratta = new Tratte(zonaPartenza, zonaArrivo, tempoPrevisto);
        TrattaDAO trattaDAO = new TrattaDAO(em);
        trattaDAO.save(userTratta);
    }

    private static void addEmBiglietto(Scanner scanner, EntityManager em) {
        System.out.println("1.Venditore");
        System.out.println("2.Distributore");
        int scelta = Integer.parseInt(scanner.nextLine());
        EmissioneBiglietti userInput;

        EmBigliettiDao emBiglDao = new EmBigliettiDao(em);

        if (scelta == 1) {
            userInput = new Rivenditore();
            emBiglDao.save(userInput);
        } else if (scelta == 2) {
            userInput = new Distributore(true);
            emBiglDao.save(userInput);

        } else System.out.println("Scelta non valida");

    }

    private static void storicoStatusMezzo(Scanner scanner, EntityManager em) {
        //deve comparire la lista dei mezzi
        MezziDAO mezziDAO = new MezziDAO(em);
        StatusDao statDao = new StatusDao(em);
        mezziDAO.listaMezzi();
        System.out.print("Seleziona indice del mezzo: ");
        long idMezzo = Integer.parseInt(scanner.nextLine());
        //poi deve essere seleziopnaro
        statDao.getPeriodoDiManutenzioneServizioByMezzo(idMezzo);

    }

    private static void validazioneUtenteAbb(Scanner scanner, EntityManager em) {
        //Cmparire lista utente id utente
        UtenteDao utDao = new UtenteDao(em);
        PassDao passDao = new PassDao(em);
        System.out.println("Lista utenti");
        utDao.listaUtenti();
        System.out.print("Inserisci l'indice utente da controllare: ");
        long utenteId = Long.parseLong(scanner.nextLine());

        //Lista abb ed id Abb
        System.out.println("Lista Abbonamenti da controllare la validità appartenenti all' utente scelto");
        passDao.listaAllAbbonamenti();
        System.out.print("Inserisci l'indice dell' abbonamneto da verificare la validità: ");
        long abbId = Long.parseLong(scanner.nextLine());
        //boolean checkabb1 = passDao.checkAbbByUtente(52, 1);
        boolean checkAbb = passDao.checkAbbByUtente(utenteId, abbId);
        if (checkAbb) {
            System.out.println("L'abbonamento passsa il controllo");
        } else System.out.println("L'abbonamento non passsa il controllo");
    }

    private static void countBigliettiVidimatiByPeriod (Scanner scanner,EntityManager em) {
        VidimatoDao vidDao = new VidimatoDao(em);
        vidDao.getBigliettiVidimatiByPeriodo(LocalDate.of(2002, 10, 15), LocalDate.of(2025, 10, 10));
    }

    private static void tempoMedioDiPercorrezaMezzo (Scanner scanner,EntityManager em) {
        ViaggioDAO viaggioDAO = new ViaggioDAO(em);
        MezziDAO mezziDAO = new MezziDAO(em);
        //lista mezzi
        mezziDAO.listaMezzi();
        System.out.print("Seleziona l'id del mezzo: ");
        long idMezzo = Long.parseLong(scanner.nextLine());
        double resultMediaTempoEffettivo = viaggioDAO.mediaTempoEffettivoByMezzo(idMezzo);
        System.out.println("La media tratta del mezzo è: "+ resultMediaTempoEffettivo);
    }

    private static void countViaggioByMezzo (Scanner scanner,EntityManager em) {
        ViaggioDAO viaggioDAO = new ViaggioDAO(em);
        MezziDAO mezziDAO = new MezziDAO(em);
        //lista mezzi
        mezziDAO.listaMezzi();
        System.out.print("Seleziona l'id del mezzo: ");
        long idMezzo = Long.parseLong(scanner.nextLine());
        long resultCountPT = viaggioDAO.countMezzoPercorreTratta(idMezzo);
        System.out.println("Il conteggio dei viaggi del mezzo sulla stessa tratta: "+ resultCountPT);
    }

    private static void controlloBigliettiPerEmissioneBIglietti (Scanner scanner,EntityManager em) {
        ViaggioDAO viaggioDAO = new ViaggioDAO(em);
        MezziDAO mezziDAO = new MezziDAO(em);
        //lista mezzi
        mezziDAO.listaMezzi();
        System.out.print("Seleziona l'id del mezzo: ");
        long idMezzo = Long.parseLong(scanner.nextLine());
        long resultCountPT = viaggioDAO.countMezzoPercorreTratta(idMezzo);
        System.out.println("Il conteggio dei viaggi del mezzo sulla stessa tratta: "+ resultCountPT);
    }

    private static void checkVenditeBiglietto(Scanner scanner, EntityManager em) {
        PassDao passDao = new PassDao(em);

        LocalDate dataInizio = LocalDate.of(2002, 3, 3);

        LocalDate dataFine = LocalDate.of(2025, 3, 3);
        List<Object[]> testQuery = passDao.numeroPassPerPeriodoEPuntoEmissione(dataInizio, dataFine);
        for (Object[] result : testQuery) {
            EmissioneBiglietti emissioneBiglietti = (EmissioneBiglietti) result[0];
            Long passCount = (Long) result[1];

            System.out.println("Punto emissione: " + emissioneBiglietti + ", Numero di biglietti e abbonamenti: " + passCount);
        }
    }

    private static void addViaggio(Scanner scanner, EntityManager em) {
        StatusDao statusDao = new StatusDao(em);
        ViaggioDAO viaggioDAO = new ViaggioDAO(em);
        System.out.println("Inserisci il tempo effettivo");
        int tempo_Effettivo = Integer.parseInt(scanner.nextLine());
        System.out.println("Data di partenza");
        System.out.print("Inserisci l'anno: ");
        int anno = Integer.parseInt(scanner.nextLine());
        System.out.print("Inserisci il mese: ");
        int mese = Integer.parseInt(scanner.nextLine());
        System.out.print("Inserisci il giorno: ");
        int giorno = Integer.parseInt(scanner.nextLine());
        System.out.print("Inserisci l'ora: ");
        int ora = Integer.parseInt(scanner.nextLine());
        System.out.print("Inserisci minuti: ");
        int minuti = Integer.parseInt(scanner.nextLine());
        LocalDateTime data_di_partenza = LocalDateTime.of(anno,mese,giorno,ora,minuti);
        statusDao.listaInServizioAttuali();
        System.out.print("Inserisci l'id del viaggio: ");
        long id_InServizio = Long.parseLong(scanner.nextLine());
        InServizio servizio = statusDao.findInServizioById(id_InServizio);
        Viaggio viag1 = new Viaggio(tempo_Effettivo,data_di_partenza,servizio);
        viaggioDAO.save(viag1);
    }

    private static void compraBiglietto(Scanner scanner, EntityManager em) {
        //deve comparire prima la lista dei distributori
        EmBigliettiDao embiDao = new EmBigliettiDao(em);
        PassDao passDao = new PassDao(em);
        //qui compare la lista
        embiDao.listaDistri();
        //selezione distributore
        System.out.print("Seleziona distributore tramite id: ");
        long idSelezionato = scanner.nextLong();
        EmissioneBiglietti disSele = embiDao.SelezionaDistributoreById(idSelezionato);

        //Creazione biglietto
        Biglietto nuovoBiglietto = new Biglietto(LocalDate.now(), disSele);
        passDao.save(nuovoBiglietto);

        System.out.println("Biglietto acquistato con successo!");
    }

    private static void visualizzaAbbonamenti(Scanner scanner, EntityManager em, long idUtente) {
        //deve comparire prima la lista dei distributori
        PassDao passDao = new PassDao(em);
        //qui compare la lista
        passDao.listaAbbonamenti(idUtente);
        //selezione distributore
        System.out.print("Seleziona Abbonamento tramite id: ");
        long idSelezionato = scanner.nextLong();
        passDao.dettagliAbbonamento(idSelezionato);
    }

    private static void dettagliTessera(Scanner scanner, EntityManager em, long idUtente) {
        TesseraDao tessDao = new TesseraDao(em);
        tessDao.dettagliTessera(idUtente);
    }

    private static void vidimaBiglietto(Scanner scanner, EntityManager em) {
        //Deve comparire la lista dei biglietti, e selezionare (controllo che non sia già vidimato)
        PassDao passDao = new PassDao(em);
        StatusDao satDao = new StatusDao(em);
        VidimatoDao vidDao = new VidimatoDao(em);

        passDao.listaBiglietti();
        System.out.print("Seleziona l'id del Biglietto: ");
        long idBigliettoSelezionato = scanner.nextLong();

        //compare la lista in servizio attuale, per selezionare il mezzo
        satDao.listaInServizioAttuali();
        System.out.print("Seleziona l'id del mezzo in servizio: ");
        long idServizioSelezionato = scanner.nextLong();

        //dobbiamo prenderci sia il biglietto che inServizio
        InServizio inServizio = satDao.findInServizioById(idServizioSelezionato);
        Biglietto biglietto = passDao.findByIdBiglietto(idBigliettoSelezionato);
        //facciamo vidimare il biglietto
        Vidimato vid1 = new Vidimato(biglietto, LocalDate.now(), inServizio);
        vidDao.save(vid1);
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
