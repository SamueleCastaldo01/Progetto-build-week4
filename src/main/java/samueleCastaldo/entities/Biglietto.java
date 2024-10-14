package samueleCastaldo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
@Entity
@Table(name = "biglietti")
public class Biglietto extends Pass {
//    @Column (name = "id_vidimato")
//    private long idVidimato;


    public Biglietto() {
    }

    public Biglietto(LocalDate dataEmissione, EmissioneBiglietti emissioneBiglietti) {
        super(dataEmissione, emissioneBiglietti);
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                '}';
    }
}
