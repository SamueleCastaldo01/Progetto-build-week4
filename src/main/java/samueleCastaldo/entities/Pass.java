package samueleCastaldo.entities;



import jakarta.persistence.*;

import java.time.LocalDate;

@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Pass {
    @Id
    @GeneratedValue
    protected long id;
    @ManyToOne
    @JoinColumn(name = "id_vendita", referencedColumnName = "id")
    protected EmissioneBiglietti emissioneBiglietti;
    @Column (name = "data_emissione", nullable = false)
    protected LocalDate dataEmissione;


    public Pass() {
    }

    public Pass(LocalDate dataEmissione,EmissioneBiglietti emissioneBiglietti) {
        this.dataEmissione = dataEmissione;
        this.emissioneBiglietti = emissioneBiglietti;
    }

    public long getId() {
        return id;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public EmissioneBiglietti getEmissioneBiglietti() {
        return emissioneBiglietti;
    }

    public void setEmissioneBiglietti(EmissioneBiglietti emissioneBiglietti) {
        this.emissioneBiglietti = emissioneBiglietti;
    }

    @Override
    public String toString() {
        return "Pass{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                '}';
    }
}
