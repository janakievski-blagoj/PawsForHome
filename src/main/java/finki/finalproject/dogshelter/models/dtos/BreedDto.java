package finki.finalproject.dogshelter.models.dtos;

import lombok.Data;

@Data
public class BreedDto {
    private String name;
    private String growth;
    private String sheddingVolume;
    private int ageExpectancyFrom;
    private int ageExpectancyTo;

    public BreedDto() {
    }

    public BreedDto(String name, String growth, String sheddingVolume, int ageExpectancyFrom, int ageExpectancyTo) {
        this.name = name;
        this.growth = growth;
        this.sheddingVolume = sheddingVolume;
        this.ageExpectancyFrom = ageExpectancyFrom;
        this.ageExpectancyTo = ageExpectancyTo;
    }
}
