package samueleCastaldo.entities;

import jakarta.persistence.*;

import javax.swing.plaf.ProgressBarUI;
import java.time.LocalDate;

@Entity
@Table(name = "abbonamenti")
public class Abbonamento extends Pass {
    @ManyToOne
    @JoinColumn(name = "id_tessera", referencedColumnName = "id")
    private Tessera tessera;
    @Column(name = "tipo_abbonamento", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;
    @Column(name = "data_scadeza")
    private LocalDate dataScadenza;

    public Abbonamento() {
    }

    public Abbonamento(LocalDate dataEmissione, TipoAbbonamento tipoAbbonamento,Tessera tessera) {
        super(dataEmissione);
        this.dataScadenza = setDataScadenza();
        this.tipoAbbonamento = tipoAbbonamento;
        this.tessera = tessera;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    public LocalDate setDataScadenza() {
        if (tipoAbbonamento == TipoAbbonamento.SETTIMANALE) {
            this.dataScadenza = dataEmissione.plusDays(7);
        } else {
            this.dataScadenza = dataEmissione.plusDays(30);
        }
        return this.dataScadenza;
    }

    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }


}
