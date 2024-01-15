package finki.finalproject.dogshelter.web.rest_controllers;

import finki.finalproject.dogshelter.models.Breed;
import finki.finalproject.dogshelter.models.dtos.BreedDto;
import finki.finalproject.dogshelter.models.expectations.BreedNotFoundException;
import finki.finalproject.dogshelter.service.IBreedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/breeds")
public class BreedController {

    private final IBreedService breedService;

    public BreedController(IBreedService breedService) {
        this.breedService = breedService;
    }

    @GetMapping()
    public List<Breed> getAllBreeds() {
        return this.breedService.listAllBreeds();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Breed> getBreedById(@PathVariable long id) {
        return this.breedService.findBreedById(id)
                .map(breed -> ResponseEntity.ok().body(breed))
                .orElseThrow(() -> new BreedNotFoundException(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Breed> addNewBreed(@RequestParam String name,
                                             @RequestParam String growth,
                                             @RequestParam String sheddingVolume,
                                             @RequestParam int ageExpectancyFrom,
                                             @RequestParam int ageExpectancyTo) {

        BreedDto breedDto = new BreedDto(name, growth, sheddingVolume, ageExpectancyFrom, ageExpectancyTo);
        return this.breedService.createNewBreed(breedDto)
                .map(breed -> ResponseEntity.ok().body(breed))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<Breed> editBreed(@PathVariable long id,
                                           @RequestParam String name,
                                           @RequestParam String growth,
                                           @RequestParam String sheddingVolume,
                                           @RequestParam int ageExpectancyFrom,
                                           @RequestParam int ageExpectancyTo) {

        BreedDto breedDto = new BreedDto(name, growth, sheddingVolume, ageExpectancyFrom, ageExpectancyTo);
        return this.breedService.editBreed(id, breedDto)
                .map(breed -> ResponseEntity.ok().body(breed))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity removeBreed(@PathVariable long id) {
        this.breedService.deleteBreedById(id);
        if (this.breedService.findBreedById(id).isEmpty())
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
