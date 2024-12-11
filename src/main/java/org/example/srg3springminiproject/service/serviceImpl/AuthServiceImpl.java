package org.example.srg3springminiproject.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.example.srg3springminiproject.repository.UserRepository;
import org.example.srg3springminiproject.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByEmail(username);
    }
}
