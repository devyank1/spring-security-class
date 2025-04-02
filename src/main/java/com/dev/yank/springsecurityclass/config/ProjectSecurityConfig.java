package com.dev.yank.springsecurityclass.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // this method is to do the basic security in application
        http.authorizeHttpRequests((requests) -> requests // lambda expression to do authenticated items and permitted items
                .requestMatchers("/account", "/balance", "/loans", "/cards").authenticated()
                .requestMatchers("/contact", "/notices", "/error", "/welcome").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}12345").authorities("read").build();
        UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$12$RMLvlcJrha.vcx5wl//8Eus3K/ZxelrcYyC6wlKQkJK9Qv.lSSDUe").authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder encoder() { // this method is to create a password encoder
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // factories it's better than encrypt because they pick any password hash.
    }

    @Bean
    public CompromisedPasswordChecker checker() { // this helps to check if pwd is compromised in any data breaches.
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
