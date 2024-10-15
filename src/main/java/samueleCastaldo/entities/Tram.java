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
    public Tram(int capienza, String status, String codiceMezzo) {
        super(capienza, status, codiceMezzo);
    }


}
