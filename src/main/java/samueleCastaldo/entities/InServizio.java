package samueleCastaldo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "in_servizio")
public class InServizio extends Status {
    //protected Tratta tratta;
    @ManyToOne
    @JoinColumn(name = "id_tratta", referencedColumnName = "id")
    private Tratte tratta;


    public InServizio() {
    }

    public InServizio(LocalDate dataFine, LocalDate dataInizio, Mezzi mezzo, Tratte tratta) {
        super(dataFine, dataInizio, mezzo);
        this.tratta = tratta;
    }

    @Override
    public String toString() {
        return "InServizio{" +
                "dataFine=" + dataFine +
                ", dataInizio=" + dataInizio +
                ", id=" + id +
                ", mezzo=" + mezzo +
                '}';
    }
}
