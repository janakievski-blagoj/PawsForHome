package finki.finalproject.dogshelter.models.expectations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BreedNotFoundException extends RuntimeException {
    public BreedNotFoundException(long id) {
        super(String.format("Breed with id %d was not found.", id));
    }
}
