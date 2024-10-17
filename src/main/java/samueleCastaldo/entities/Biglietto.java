package samueleCastaldo.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "biglietti")
public class Biglietto extends Pass {
//    @Column (name = "id_vidimato")
//    private long idVidimato;
    private boolean convalida;


    public Biglietto() {
    }

    public Biglietto(LocalDate dataEmissione, EmissioneBiglietti emissioneBiglietti) {
        super(dataEmissione, emissioneBiglietti);
        this.convalida = false;
    }

    public void setConvalidaTrue() {
        this.convalida = true;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                ", id=" + id +
                ", convalida=" + convalida +
                ", emissioneBiglietti=" + emissioneBiglietti +
                ", dataEmissione=" + dataEmissione +
                '}';
    }
}
