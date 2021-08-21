package ai.tech.blog.user.domain.service;

import ai.tech.blog.common.security.jwt.JwtProvider;
import ai.tech.blog.user.application.dto.AuthenticationResponse;
import ai.tech.blog.user.application.dto.SignInRequest;
import ai.tech.blog.user.application.dto.SignUpRequest;
import ai.tech.blog.user.domain.model.User;
import ai.tech.blog.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;

    @Autowired
    public void setUserRepository(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationManager(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtProvider(final JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public void signUp(final SignUpRequest signUpRequest) {
        final User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .build();
        userRepository.save(user);
    }

    public AuthenticationResponse signIn(final SignInRequest signInRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwt(authentication);
        return new AuthenticationResponse(jwt, signInRequest.getUsername());
    }

    public Optional<org.springframework.security.core.userdetails.User> getSignedInUser() {
        final org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(user);
    }
}