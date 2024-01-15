package finki.finalproject.dogshelter.service;

import finki.finalproject.dogshelter.models.User;
import finki.finalproject.dogshelter.models.dtos.RegisterDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    User register(RegisterDto registerDto);
}
