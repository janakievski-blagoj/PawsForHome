package finki.finalproject.dogshelter.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private LocalDate birthday;

    @ManyToOne
    private Breed breed;

    private String photoUrl;

    private boolean certified;

    private boolean vaccinated;

    public Dog() {
    }

    public Dog(String name, Breed breed, LocalDate birthday, String photoUrl, boolean certified, boolean vaccinated) {
        this.name = name;
        this.breed = breed;
        this.birthday = birthday;
        this.photoUrl = photoUrl;
        this.certified = certified;
        this.vaccinated = vaccinated;
    }
}
