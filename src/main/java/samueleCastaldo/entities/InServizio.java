package samueleCastaldo.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
@Entity
@Table(name = "in_servizio")

public class InServizio extends Status{
//protected Tratta tratta;


    public InServizio() {
    }

    public InServizio(LocalDate dataFine, LocalDate dataInizio, Mezzi mezzo) {
        super(dataFine, dataInizio, mezzo);
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
