package samueleCastaldo.entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tessere")
public class Tessera {
    @Id
    @GeneratedValue
    private long id;
    private LocalDate data_emissione;
    private LocalDate data_scadenza;
    @OneToOne
    @JoinColumn(name = "utente_id", referencedColumnName = "id")
    private Utente utente;

    public Tessera(){}

    public Tessera(LocalDate data_emissione, Utente utente) {
        this.data_emissione = data_emissione;
        this.data_scadenza = setData_scadenza(data_emissione);
        this.utente = utente;
    }

    public long getId() {
        return id;
    }

    public LocalDate getData_emissione() {
        return data_emissione;
    }

    public LocalDate getData_scadenza() {
        return data_scadenza;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setData_emissione(LocalDate data_emissione) {
        this.data_emissione = data_emissione;
    }

    public LocalDate setData_scadenza(LocalDate data_scadenza) {
        data_scadenza = data_emissione.plusYears(1);
        return data_scadenza;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + id +
                ", data_emissione=" + data_emissione +
                ", data_scadenza=" + data_scadenza +
                ", utente=" + utente +
                '}';
    }
}
