package samueleCastaldo.entities;
import jakarta.persistence.*;
@Entity
@DiscriminatorValue("AUTOBUS")

public class Autobus extends Mezzi {



    public Autobus() {
        super();
    }
    public Autobus(int capienza, String status, String codiceMezzo) {
        super(capienza, status, codiceMezzo);
    }


    @Override
    public String toString() {
        return "Autobus{" +
                "id=" + id +
                ", capienza=" + capienza +
                ", status='" + status + '\'' +
                ", codiceMezzo='" + codiceMezzo + '\'' +
                '}';
    }
}
