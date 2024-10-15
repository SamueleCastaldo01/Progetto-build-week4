package samueleCastaldo.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;
@Entity
@DiscriminatorValue("IN MANUTENZIONE")

public class InManutenzione extends Status{
    public InManutenzione() {
    }

    public InManutenzione(LocalDate dataFine, LocalDate dataInizio, Mezzi mezzo) {
        super(dataFine, dataInizio, mezzo);
    }
}
