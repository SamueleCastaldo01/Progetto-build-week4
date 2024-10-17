package samueleCastaldo.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "vidimato")
public class Vidimato {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    @JoinColumn(name = "biglietto_id",unique = true)
    private Biglietto biglietto;
    @ManyToOne
    @JoinColumn(name = "id_servizio", referencedColumnName = "id")
    private InServizio servizio;
    private LocalDate data_convalida;

    public Vidimato() {
    }

    public Vidimato(Biglietto biglietto, LocalDate data_convalida, InServizio servizio) {
        this.biglietto = biglietto;
        this.data_convalida = data_convalida;
        this.servizio = servizio;
        this.biglietto.setConvalidaTrue();
    }

    public long getId() {
        return id;
    }


    public Biglietto getBiglietto() {
        return biglietto;
    }

    public void setBiglietto(Biglietto biglietto) {
        this.biglietto = biglietto;
    }



    public LocalDate getData_convalida() {
        return data_convalida;
    }

    public void setData_convalida(LocalDate data_convalida) {
        this.data_convalida = data_convalida;
    }

    @Override
    public String toString() {
        return "Vidimato{" +
                "id=" + id +
                ", biglietto=" + biglietto +
                ", data_convalida=" + data_convalida +
                '}';
    }


}
