package finki.finalproject.dogshelter.service.impl;

import finki.finalproject.dogshelter.models.Breed;
import finki.finalproject.dogshelter.models.dtos.BreedDto;
import finki.finalproject.dogshelter.models.enums.Growth;
import finki.finalproject.dogshelter.models.enums.Shed;
import finki.finalproject.dogshelter.models.expectations.BreedNotFoundException;
import finki.finalproject.dogshelter.repository.BreedRepository;
import finki.finalproject.dogshelter.service.IBreedService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BreedService implements IBreedService {

    private final BreedRepository breedRepository;

    public BreedService(BreedRepository breedRepository) {
        this.breedRepository = breedRepository;
    }

    @Override
    public List<Breed> listAllBreeds() {
        return this.breedRepository.findAll();
    }

    @Override
    public Optional<Breed> findBreedById(long id) {
        return this.breedRepository.findById(id);
    }

    @Override
    public Optional<Breed> createNewBreed(BreedDto breedDto) {
        if (breedDto.getName() == null || breedDto.getName().isEmpty() ||
                breedDto.getGrowth() == null || breedDto.getSheddingVolume() == null ||
                breedDto.getAgeExpectancyFrom() < 0 || breedDto.getAgeExpectancyTo() > 20) {

            throw new IllegalArgumentException();
        }

        Growth growth = Growth.valueOf(breedDto.getGrowth());
        Shed sheddingVolume = Shed.valueOf(breedDto.getSheddingVolume());
        Breed breed = new Breed(breedDto.getName(), growth, sheddingVolume, breedDto.getAgeExpectancyFrom(), breedDto.getAgeExpectancyTo());
        return Optional.of(this.breedRepository.save(breed));

    }

    @Override
    public Optional<Breed> editBreed(long id, BreedDto breedDto) {
        if (breedDto == null || breedDto.getName() == null || breedDto.getName().isEmpty() ||
                breedDto.getGrowth() == null || breedDto.getSheddingVolume() == null ||
                breedDto.getAgeExpectancyFrom() < 0 || breedDto.getAgeExpectancyTo() > 20) {

            throw new IllegalArgumentException();
        }

        Breed breed = this.breedRepository.findById(id).orElseThrow(() -> new BreedNotFoundException(id));
        breed.setName(breedDto.getName());
        breed.setGrowth(Growth.valueOf(breedDto.getGrowth()));
        breed.setSheddingVolume(Shed.valueOf(breedDto.getSheddingVolume()));
        breed.setAgeExpectancyFrom(breedDto.getAgeExpectancyFrom());
        breed.setAgeExpectancyTo(breed.getAgeExpectancyTo());

        return Optional.of(this.breedRepository.save(breed));
    }

    @Override
    public void deleteBreedById(long id) {
        this.breedRepository.deleteById(id);
    }

}
