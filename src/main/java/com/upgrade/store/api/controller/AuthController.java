package com.upgrade.store.api.controller;

import com.upgrade.store.api.dto.request.AuthRequest;
import com.upgrade.store.api.dto.request.RegisterRequest;
import com.upgrade.store.api.dto.response.AuthResponse;
import com.upgrade.store.domain.model.Role;
import com.upgrade.store.domain.model.RoleName;
import com.upgrade.store.domain.model.User;
import com.upgrade.store.domain.model.UserAccount;
import com.upgrade.store.domain.repository.RoleRepository;
import com.upgrade.store.domain.repository.UserAccountRepository;
import com.upgrade.store.domain.repository.UserRepository;
import com.upgrade.store.security.jwt.JwtService;
import com.upgrade.store.security.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {
        if (userRepository.findByEmail(req.email()).isPresent()) {
            log.warn("Attempt to register with existing email: {}", req.email());
            return ResponseEntity.badRequest().body(new AuthResponse("Email already in use"));
        }

        Role defaultRole = roleRepository.findByName(RoleName.CUSTOMER)
                .orElseThrow(() -> new IllegalStateException("Default role not found in DB"));

        User user = User.builder()
                .withEmail(req.email())
                .withPasswordHash(passwordEncoder.encode(req.password()))
                .withRoles(new HashSet<>(List.of(defaultRole)))
                .build();

        userRepository.save(user);

        UserAccount account = UserAccount.builder()
                .withUser(user)
                .withFirstName(req.firstName())
                .withLastName(req.lastName())
                .withPhoneNumber(req.phoneNumber())
                .build();

        userAccountRepository.save(account);

        String token = jwtService.generateToken(new UserPrincipal(user));
        log.info("User registered: {}", req.email());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest req) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.email(), req.password())
            );
        } catch (Exception ex) {
            log.warn("Failed login attempt for {}: {}", req.email(), ex.getMessage());
            return ResponseEntity.status(401).body(new AuthResponse("Invalid credentials"));
        }

        User user = userRepository.findByEmail(req.email()).orElseThrow();
        String token = jwtService.generateToken(new UserPrincipal(user));
        log.info("User authenticated: {}", req.email());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
