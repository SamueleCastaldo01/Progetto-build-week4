package samueleCastaldo.entities;
import jakarta.persistence.*;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Mezzi {

    @Id
    @GeneratedValue
    protected long id;
    @Column(nullable = false)
    protected int capienza;
    @Column(nullable = false)
    protected String status;
    @Column(name = "codice_mezzo", nullable = false, unique = true)
    protected String codiceMezzo;

    public Mezzi() {}

    public Mezzi(int capienza, String status, String codiceMezzo) {
        this.capienza = capienza;
        this.status = status;
        this.codiceMezzo = codiceMezzo;
    }
    public long getId() {
        return id;
    }

    public int getCapienza() {
        return capienza;
    }
    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCodiceMezzo() {
        return codiceMezzo;
    }
    public void setCodiceMezzo(String codiceMezzo) {
        this.codiceMezzo = codiceMezzo;
    }
}

