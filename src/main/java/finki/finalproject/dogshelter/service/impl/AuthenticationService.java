package finki.finalproject.dogshelter.service.impl;

import finki.finalproject.dogshelter.models.User;
import finki.finalproject.dogshelter.models.expectations.InvalidArgumentsException;
import finki.finalproject.dogshelter.models.expectations.InvalidUserCredentialsException;
import finki.finalproject.dogshelter.repository.UserRepository;
import finki.finalproject.dogshelter.service.IAuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        return this.userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }
}
