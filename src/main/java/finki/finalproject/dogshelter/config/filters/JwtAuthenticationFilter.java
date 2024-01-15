package finki.finalproject.dogshelter.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finki.finalproject.dogshelter.config.constants.JwtAuthConstants;
import finki.finalproject.dogshelter.models.User;
import finki.finalproject.dogshelter.models.dtos.UserDetailsDto;
import finki.finalproject.dogshelter.models.expectations.PasswordsDoNotMatchException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.setAuthenticationManager(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User credentials = null;
        try {
            String text = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println(text);
            credentials = new ObjectMapper().readValue(text, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (credentials == null) {
            return super.attemptAuthentication(request, response);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getUsername());
        if (!passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword())) {
            throw new PasswordsDoNotMatchException();
        }

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), credentials.getPassword(), userDetails.getAuthorities()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.generateJwt(response, authResult);
        super.successfulAuthentication(request, response, chain, authResult);
    }

    public String generateJwt(HttpServletResponse response, Authentication authResult) throws JsonProcessingException {
        User userDetails = (User) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(new ObjectMapper().writeValueAsString(UserDetailsDto.of(userDetails)))
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtAuthConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(JwtAuthConstants.SECRET.getBytes()));
        response.addHeader(JwtAuthConstants.HEADER_STRING, JwtAuthConstants.TOKEN_PREFIX + token);
        return JwtAuthConstants.TOKEN_PREFIX + token;
    }
}

