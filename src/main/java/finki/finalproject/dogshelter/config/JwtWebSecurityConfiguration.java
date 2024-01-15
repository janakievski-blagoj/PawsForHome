package finki.finalproject.dogshelter.config;


import finki.finalproject.dogshelter.config.filters.JwtAuthenticationFilter;
import finki.finalproject.dogshelter.config.filters.JwtAuthorizationFilter;
import finki.finalproject.dogshelter.service.impl.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class JwtWebSecurityConfiguration {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserService userService;


    public JwtWebSecurityConfiguration(PasswordEncoder passwordEncoder,
                                       AuthenticationConfiguration authenticationConfiguration,
                                       UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationConfiguration = authenticationConfiguration;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/login", "/api/register", "/api/**").permitAll()
                .requestMatchers("/api/dogs/add", "/api/edit/*").hasRole("[ROLE_ADMIN]")
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration), userService, passwordEncoder))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(authenticationConfiguration)))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}