package com.raf.airportuserservice.security;

import com.raf.airportuserservice.domain.Admin;
import com.raf.airportuserservice.domain.User;
import com.raf.airportuserservice.repository.AdminRepository;
import com.raf.airportuserservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static java.util.Collections.emptyList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private PasswordEncoder encoder;
    private UserRepository userRepo;
    private AdminRepository adminRepository;

    @Autowired
    public CustomAuthenticationProvider(UserRepository userRepo, AdminRepository adminRepository) {
        super();
        this.userRepo = userRepo;
        this.adminRepository = adminRepository;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String email = auth.getName();
        String password = auth.getCredentials().toString();

        Admin admin = adminRepository.findByUsername(email);
        if (admin != null) {
            if (encoder.matches(password, admin.getPassword())) {
                return new UsernamePasswordAuthenticationToken(email, password, emptyList());
            }
        }

        User user = userRepo.findByEmail(email);

        if (user == null) {
            throw new BadCredentialsException("Authentication failed");
        }

        // proveri sifru
        if (encoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(email, password, emptyList());
        }

        throw new BadCredentialsException("Authentication failed");
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }
}
