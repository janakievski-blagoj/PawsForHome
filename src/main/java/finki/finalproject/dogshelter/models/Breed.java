package finki.finalproject.dogshelter.models;

import finki.finalproject.dogshelter.models.enums.Growth;
import finki.finalproject.dogshelter.models.enums.Shed;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Growth growth;

    private int ageExpectancyFrom;

    private int ageExpectancyTo;

    @Enumerated(EnumType.STRING)
    private Shed sheddingVolume;

    public Breed() {
    }

    public Breed(String name, Growth growth, Shed sheddingVolume, int ageExpectancyFrom, int ageExpectancyTo) {
        this.name = name;
        this.growth = growth;
        this.sheddingVolume = sheddingVolume;
        this.ageExpectancyFrom = ageExpectancyFrom;
        this.ageExpectancyTo = ageExpectancyTo;
    }
}
