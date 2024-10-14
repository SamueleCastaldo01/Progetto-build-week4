package samueleCastaldo.entities;



import jakarta.persistence.*;

import java.time.LocalDate;

@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Pass {
    @Id
    @GeneratedValue
    protected long id;
    //    @Column(name = "id_emissione", nullable = false)
//    protected long idEmissione;
    @Column (name = "data_emissione", nullable = false)
    protected LocalDate dataEmissione;


    public Pass() {
    }

    public Pass(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
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

    @Override
    public String toString() {
        return "Pass{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                '}';
    }
}
