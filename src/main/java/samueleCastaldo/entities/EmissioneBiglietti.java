package samueleCastaldo.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_emissione", discriminatorType = DiscriminatorType.STRING)
public abstract class EmissioneBiglietti {
    @Id
    @GeneratedValue
    private long id;

    public EmissioneBiglietti() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EmissioneBiglietti{" +
                "id=" + id +
                '}';
    }
}
