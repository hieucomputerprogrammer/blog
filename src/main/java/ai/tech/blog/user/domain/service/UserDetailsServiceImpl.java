package ai.tech.blog.user.domain.service;

import ai.tech.blog.user.domain.model.User;
import ai.tech.blog.user.domain.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User foundUser = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with username: " + username + " does not exist.")
                );
        return new org.springframework.security.core.userdetails.User(
                foundUser.getUsername(),
                foundUser.getPassword(),
                true, true, true, true,
                getAuthorities("ROLE_USER")
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}