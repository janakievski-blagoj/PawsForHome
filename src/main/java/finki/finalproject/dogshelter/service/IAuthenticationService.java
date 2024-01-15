package finki.finalproject.dogshelter.service;

import finki.finalproject.dogshelter.models.User;

public interface IAuthenticationService {
    User login(String username, String password);
}
