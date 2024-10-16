package samueleCastaldo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Tratte {
    @Id
    @GeneratedValue
    private long id;

    private String zona_partenza;
    private String zona_arrivo;
    private int tempo_previsto;

    public Tratte() {
    }

    public Tratte(String zona_partenza, String zona_arrivo, int tempo_previsto) {
        this.zona_partenza = zona_partenza;
        this.zona_arrivo = zona_arrivo;
        this.tempo_previsto = tempo_previsto;
    }

    public long getId() {
        return id;
    }

    public String getZona_partenza() {
        return zona_partenza;
    }

    public void setZona_partenza(String zona_partenza) {
        this.zona_partenza = zona_partenza;
    }

    public String getZona_arrivo() {
        return zona_arrivo;
    }

    public void setZona_arrivo(String zona_arrivo) {
        this.zona_arrivo = zona_arrivo;
    }

    public int getTempo_previsto() {
        return tempo_previsto;
    }

    public void setTempo_previsto(int tempo_previsto) {
        this.tempo_previsto = tempo_previsto;
    }

    @Override
    public String toString() {
        return "Tratta{" +
                "zona_arrivo='" + zona_arrivo + '\'' +
                ", tempo_previsto=" + tempo_previsto +
                ", zona_partenza='" + zona_partenza + '\'' +
                ", id=" + id +
                '}';
    }
}
