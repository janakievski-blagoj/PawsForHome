package finki.finalproject.dogshelter.models.expectations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BreedWithNameNotFoundException extends RuntimeException {
    public BreedWithNameNotFoundException(String name) {
        super(String.format("Breed with name: %d was not found", name));
    }
}
