package finki.finalproject.dogshelter.models.expectations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DogNotFoundException extends RuntimeException {
    public DogNotFoundException(long id) {
        super(String.format("Dog with id %d was not found.", id));
    }
}
