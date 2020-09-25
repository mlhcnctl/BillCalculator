package com.BillCalculator.auth;

import com.BillCalculator.entity.UserEntity;
import com.BillCalculator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override // spring'in kendi loadUserByUsername metodu default cagiracak
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserName(username);
        if (userEntity != null && userEntity.isConfirmed() == true) {
            return userEntity;
        }
        throw new UsernameNotFoundException(username);
    }

}
