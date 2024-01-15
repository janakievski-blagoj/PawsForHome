package finki.finalproject.dogshelter.service;

import finki.finalproject.dogshelter.models.Breed;
import finki.finalproject.dogshelter.models.dtos.BreedDto;

import java.util.List;
import java.util.Optional;

public interface IBreedService {
    List<Breed> listAllBreeds();

    Optional<Breed> findBreedById(long id);

    Optional<Breed> createNewBreed(BreedDto breedDto);

    Optional<Breed> editBreed(long id, BreedDto breedDto);

    void deleteBreedById(long id);
}
