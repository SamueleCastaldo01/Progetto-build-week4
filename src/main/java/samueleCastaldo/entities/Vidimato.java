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
    @JoinColumn(name = "biglietto_id")
    private Biglietto biglietto;
    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzi mezzo;
    private LocalDate data_convalida;

    public Vidimato(Biglietto biglietto, Mezzi mezzo, LocalDate data_convalida) {
        this.biglietto = biglietto;
        this.mezzo = mezzo;
        this.data_convalida = data_convalida;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Biglietto getBiglietto() {
        return biglietto;
    }

    public void setBiglietto(Biglietto biglietto) {
        this.biglietto = biglietto;
    }

    public Mezzi getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzi mezzo) {
        this.mezzo = mezzo;
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
                ", mezzo=" + mezzo +
                ", data_convalida=" + data_convalida +
                '}';
    }
}
