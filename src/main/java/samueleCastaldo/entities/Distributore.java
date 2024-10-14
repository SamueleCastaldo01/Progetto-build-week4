package samueleCastaldo.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Distributore")
public class Distributore extends EmissioneBiglietti {

    private boolean attivo;

    public Distributore() {
    }

    public Distributore(boolean attivo) {
        this.attivo = attivo;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    @Override
    public String toString() {
        return "Distributore{" +
                "attivo=" + attivo +
                '}';
    }
}
