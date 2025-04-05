package com.dev.yank.springsecurityclass.config;

import com.dev.yank.springsecurityclass.model.Customer;
import com.dev.yank.springsecurityclass.repository.CustomerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class SecurityDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public SecurityDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override //
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User details not found for the user: " + username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));

        return new User(customer.getEmail(), customer.getPassword(), authorities);
    }


}
