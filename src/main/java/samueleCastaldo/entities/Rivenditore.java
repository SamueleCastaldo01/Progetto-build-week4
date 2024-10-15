package samueleCastaldo.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Rivenditore")
public class Rivenditore extends EmissioneBiglietti {

    public Rivenditore() {
    }

    public String toString() {
        return "Rivenditore{" +
                "id= " +getId()+
                '}';
    }


}
