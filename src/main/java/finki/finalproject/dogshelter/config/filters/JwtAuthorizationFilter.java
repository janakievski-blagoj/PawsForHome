package finki.finalproject.dogshelter.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finki.finalproject.dogshelter.config.constants.JwtAuthConstants;
import finki.finalproject.dogshelter.models.dtos.UserDetailsDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtAuthConstants.HEADER_STRING);
        if (header == null || !header.startsWith(JwtAuthConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken token = getToken(header);
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }

    public UsernamePasswordAuthenticationToken getToken(String header) throws JsonProcessingException {
        String user = JWT.require(Algorithm.HMAC256(JwtAuthConstants.SECRET.getBytes()))
                .build()
                .verify(header.replace(JwtAuthConstants.TOKEN_PREFIX, ""))
                .getSubject();
        if (user == null) {
            return null;
        }
        UserDetailsDto userDetails = new ObjectMapper().readValue(user, UserDetailsDto.class);
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", Collections.singleton(userDetails.getRole()));
    }

}
