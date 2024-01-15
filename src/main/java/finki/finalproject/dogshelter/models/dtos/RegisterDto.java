package finki.finalproject.dogshelter.models.dtos;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String repeatedPassword;
    private String name;
    private String surname;
}
