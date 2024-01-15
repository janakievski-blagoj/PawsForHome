package finki.finalproject.dogshelter.web.rest_controllers;

import finki.finalproject.dogshelter.models.Dog;
import finki.finalproject.dogshelter.models.dtos.DogDto;
import finki.finalproject.dogshelter.models.expectations.DogNotFoundException;
import finki.finalproject.dogshelter.service.IDogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/dogs")
public class DogController {

    private final IDogService dogService;

    public DogController(IDogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping()
    public List<Dog> getAllDogs() {
        return this.dogService.listAllDogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dog> getDogById(@PathVariable long id) {
        return this.dogService.findDogById(id)
                .map(dog -> ResponseEntity.ok().body(dog))
                .orElseThrow(() -> new DogNotFoundException(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Dog> addNewDog(@RequestBody DogDto dogDto) {

        return this.dogService.createNewDog(dogDto)
                .map(dog -> ResponseEntity.ok().body(dog))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Dog> editDog(@PathVariable long id,
                                       @RequestBody DogDto dogDto) {

        return this.dogService.editDog(id, dogDto)
                .map(dog -> ResponseEntity.ok().body(dog))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity removeDogFromShelter(@PathVariable long id) {
        this.dogService.deleteDogById(id);
        if (this.dogService.findDogById(id).isEmpty())
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
