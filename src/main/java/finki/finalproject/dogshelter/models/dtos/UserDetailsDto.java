package finki.finalproject.dogshelter.models.dtos;

import finki.finalproject.dogshelter.models.User;
import finki.finalproject.dogshelter.models.enums.Role;
import lombok.Data;

@Data
public class UserDetailsDto {
    private String username;
    private Role role;

    public static UserDetailsDto of(User user) {
        UserDetailsDto details = new UserDetailsDto();
        details.username = user.getUsername();
        details.role = user.getRole();
        return details;
    }
}
