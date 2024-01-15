package finki.finalproject.dogshelter.models;

import finki.finalproject.dogshelter.models.enums.CartStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime dateCreated;

    @Enumerated(EnumType.STRING)
    private CartStatus status;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Dog> dogs;

    public Cart() {
    }
}
