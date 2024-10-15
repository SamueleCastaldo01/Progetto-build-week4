package samueleCastaldo.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
@Entity
@DiscriminatorValue("IN SERVIZIO")

public class InServizio extends Status{
//protected Tratta tratta;


    public InServizio() {
    }

    public InServizio(LocalDate dataFine, LocalDate dataInizio, Mezzi mezzo) {
        super(dataFine, dataInizio, mezzo);
    }
}
