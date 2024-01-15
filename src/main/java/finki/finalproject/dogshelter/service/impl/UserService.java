package finki.finalproject.dogshelter.service.impl;

import finki.finalproject.dogshelter.models.User;
import finki.finalproject.dogshelter.models.dtos.RegisterDto;
import finki.finalproject.dogshelter.models.expectations.InvalidUsernameOrPasswordException;
import finki.finalproject.dogshelter.models.expectations.PasswordsDoNotMatchException;
import finki.finalproject.dogshelter.models.expectations.UsernameAlreadyExistsException;
import finki.finalproject.dogshelter.repository.UserRepository;
import finki.finalproject.dogshelter.service.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User register(RegisterDto registerDto) {
        if (registerDto.getUsername() == null || registerDto.getUsername().isEmpty()
                || registerDto.getPassword() == null || registerDto.getPassword().isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!registerDto.getPassword().equals(registerDto.getRepeatedPassword()))
            throw new PasswordsDoNotMatchException();
        if (this.userRepository.findByUsername(registerDto.getUsername()).isPresent())
            throw new UsernameAlreadyExistsException(registerDto.getUsername());
        User user = new User(registerDto.getUsername(), passwordEncoder.encode(registerDto.getPassword()), registerDto.getName(), registerDto.getSurname());
        return userRepository.save(user);
    }
}
