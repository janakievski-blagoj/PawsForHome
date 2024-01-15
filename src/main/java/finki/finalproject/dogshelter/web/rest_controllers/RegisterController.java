package finki.finalproject.dogshelter.web.rest_controllers;

import finki.finalproject.dogshelter.models.dtos.RegisterDto;
import finki.finalproject.dogshelter.models.expectations.InvalidArgumentsException;
import finki.finalproject.dogshelter.models.expectations.PasswordsDoNotMatchException;
import finki.finalproject.dogshelter.service.impl.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String doRegister(@RequestBody RegisterDto registerDto){
        try {
            this.userService.register(registerDto);
            return "redirect:/api/login";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException ex) {
            return "redirect:/api/login?=error=" + ex.getMessage();
        }
    }
}
