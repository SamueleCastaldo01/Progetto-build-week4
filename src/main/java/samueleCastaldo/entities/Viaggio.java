package samueleCastaldo.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "viaggi")
public class Viaggio {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private int tempo_effettivo;
    @Column(nullable = false)
    private LocalDate data_partenza;
    @ManyToOne
    @JoinColumn(name = "id_servizio", referencedColumnName = "id" )
    private InServizio servizio;

    public Viaggio(int tempo_effettivo, LocalDate data_partenza, InServizio servizio) {
        this.tempo_effettivo = tempo_effettivo;
        this.data_partenza = data_partenza;
        this.servizio = servizio;
    }
    public Viaggio() {}

    public long getId() {
        return id;
    }


    public int getTempo_effettivo() {
        return tempo_effettivo;
    }

    public void setTempo_effettivo(int tempo_effettivo) {
        this.tempo_effettivo = tempo_effettivo;
    }

    public LocalDate getData_partenza() {
        return data_partenza;
    }

    public void setData_partenza(LocalDate data_partenza) {
        this.data_partenza = data_partenza;
    }

    public InServizio getServizio() {
        return servizio;
    }

    public void setServizio(InServizio servizio) {
        this.servizio = servizio;
    }

    @Override
    public String toString() {
        return "Viaggio{" +
                "id=" + id +
                ", tempo_effettivo=" + tempo_effettivo +
                ", data_partenza=" + data_partenza +
                ", servizio=" + servizio +
                '}';
    }
}
