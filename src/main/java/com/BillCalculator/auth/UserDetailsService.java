package com.BillCalculator.auth;

import com.BillCalculator.entity.UserEntity;
import com.BillCalculator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override // springin kendi loadUserByUsername metodu default cagiracak
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserName(username);
        return optionalUserEntity.orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
