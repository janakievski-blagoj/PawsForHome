package finki.finalproject.dogshelter.service;

import finki.finalproject.dogshelter.models.Dog;
import finki.finalproject.dogshelter.models.dtos.DogDto;

import java.util.List;
import java.util.Optional;

public interface IDogService {
    List<Dog> listAllDogs();

    Optional<Dog> findDogById(long id);

    Optional<Dog> createNewDog(DogDto dogDto);

    Optional<Dog> editDog(long id, DogDto dogDto);

    void deleteDogById(long id);
}
