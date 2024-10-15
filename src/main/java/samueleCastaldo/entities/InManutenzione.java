package samueleCastaldo.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
@Entity
@Table (name = "in_manutenzione")
public class InManutenzione extends Status{
    public InManutenzione() {
    }

    public InManutenzione(LocalDate dataFine, LocalDate dataInizio, Mezzi mezzo) {
        super(dataFine, dataInizio, mezzo);
    }

    @Override
    public String toString() {
        return "InManutenzione{" +
                "mezzo=" + mezzo +
                ", id=" + id +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                '}';
    }
}
