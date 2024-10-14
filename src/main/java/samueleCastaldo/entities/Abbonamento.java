package samueleCastaldo.entities;

import jakarta.persistence.*;

import javax.swing.plaf.ProgressBarUI;
import java.time.LocalDate;

@Entity
@Table(name = "abbonamenti")
public class Abbonamento extends Pass {
    //    @Column (name = "id_tessera", nullable = false)
//    private long idTessera;
    @Column (name = "tipo_abbonamento",nullable = false)
    @Enumerated (EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;
    @Column(name = "data_scadeza")
    private LocalDate dataScadenza;

    public Abbonamento() {
    }

    public Abbonamento(LocalDate dataEmissione, TipoAbbonamento tipoAbbonamento) {
        super(dataEmissione);
        this.dataScadenza = setDataScadenza(dataEmissione);
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    public LocalDate setDataScadenza(LocalDate dataScadenza) {
        if (tipoAbbonamento.equals(TipoAbbonamento.SETTIMANALE)) {
            dataScadenza = dataEmissione.plusDays(7);
        } else {
            dataScadenza = dataEmissione.plusDays(30);
        }
        return dataScadenza;
    }

    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }


}
