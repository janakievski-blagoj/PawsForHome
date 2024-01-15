package finki.finalproject.dogshelter.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import finki.finalproject.dogshelter.models.Breed;
import finki.finalproject.dogshelter.models.Dog;
import finki.finalproject.dogshelter.models.dtos.DogDto;
import finki.finalproject.dogshelter.models.expectations.BreedNotFoundException;
import finki.finalproject.dogshelter.models.expectations.DogNotFoundException;
import finki.finalproject.dogshelter.repository.BreedRepository;
import finki.finalproject.dogshelter.repository.DogRepository;
import finki.finalproject.dogshelter.service.IDogService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

@Service
public class DogService implements IDogService {

    private final DogRepository dogRepository;
    private final BreedRepository breedRepository;

    public DogService(DogRepository dogRepository, BreedRepository breedRepository) {
        this.dogRepository = dogRepository;
        this.breedRepository = breedRepository;
    }

    @Override
    public List<Dog> listAllDogs() {
        return this.dogRepository.findAll();
    }

    @Override
    public Optional<Dog> findDogById(long id) {
        return this.dogRepository.findById(id);
    }

    @Override
    public Optional<Dog> createNewDog(DogDto dogDto) {
        if (dogDto.getName() == null || dogDto.getName().isEmpty() ||
                dogDto.getBreedId() < 1) {

            throw new IllegalArgumentException();
        }

        Breed breed = this.breedRepository.findById(dogDto.getBreedId())
                .orElseThrow(() -> new BreedNotFoundException(dogDto.getBreedId()));

        String photoUrl = getPhotoURL(breed.getName());

        Dog dog = new Dog(dogDto.getName(), breed, dogDto.getBirthday(), photoUrl, dogDto.isCertified(), dogDto.isVaccinated());
        return Optional.of(this.dogRepository.save(dog));
    }

    @Override
    public Optional<Dog> editDog(long id, DogDto dogDto) {
        if (dogDto.getName() == null || dogDto.getName().isEmpty()) {

            throw new IllegalArgumentException();
        }

        Dog dog = this.dogRepository.findById(id)
                .orElseThrow(() -> new DogNotFoundException(id));

        dog.setName(dogDto.getName());
        dog.setBirthday(dogDto.getBirthday());
        dog.setCertified(dogDto.isCertified());
        dog.setVaccinated(dogDto.isVaccinated());

        return Optional.of(this.dogRepository.save(dog));
    }

    @Override
    public void deleteDogById(long id) {
        this.dogRepository.deleteById(id);
    }

    private String getPhotoURL(String breedName) {
        String photoUrl = "https://sugarplumnannies.com/wp-content/uploads/2015/11/dog-placeholder.jpg";
        try {
            URL url = new URL("https://dog.ceo/api/breed/" + breedName.replace(" ", "").toLowerCase() + "/images/random");
            URLConnection request = url.openConnection();
            request.connect();
            JsonObject response = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent())).getAsJsonObject();
            if (String.valueOf(response.get("status")).equals("\"success\""))
                photoUrl = String.valueOf(response.get("message")).replace("\"", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return photoUrl;
    }
}
