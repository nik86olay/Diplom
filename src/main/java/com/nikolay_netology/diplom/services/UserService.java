package com.nikolay_netology.diplom.services;

import com.nikolay_netology.diplom.model.UserDate;
import com.nikolay_netology.diplom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDate user = userRepository.findByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", login));
        }

        return user;
    }
}
