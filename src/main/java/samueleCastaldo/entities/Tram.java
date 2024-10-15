package samueleCastaldo.entities;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("TRAM")

public class Tram extends Mezzi {

    @Id
    private long id;

    public Tram() {
        super();
    }
    public Tram(int capienza, String codiceMezzo) {
        super(capienza, codiceMezzo);
    }

    @Override
    public String toString() {
        return "Tram{" +
                "id=" + id +
                ", capienza=" + capienza +
                ", codiceMezzo='" + codiceMezzo + '\'' +
                '}';
    }
}
