package samueleCastaldo.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Status {
    @Id
    @GeneratedValue
    protected long  id;
    @Column(name = "data_inizio",  nullable = false)
    protected LocalDate dataInizio;
    @Column(name = "data_fine")
    protected LocalDate dataFine;
    @ManyToOne
    @JoinColumn (name = "id_mezzo", referencedColumnName = "id")
    protected Mezzi mezzo;


    public Status() {
    }

    public Status(LocalDate dataFine, LocalDate dataInizio, Mezzi mezzo) {
        this.dataFine = dataFine;
        this.dataInizio = dataInizio;
        this.mezzo = mezzo;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public long getId() {
        return id;
    }


    public Mezzi getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzi mezzo) {
        this.mezzo = mezzo;
    }
}
