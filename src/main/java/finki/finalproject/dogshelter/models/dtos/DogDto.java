package finki.finalproject.dogshelter.models.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DogDto {

    private String name;
    private long breedId;
    private LocalDate birthday;
    private boolean certified;
    private boolean vaccinated;
}
