package com.m2i.demomedical.security;

import com.m2i.demomedical.entities.UserEntity;
import com.m2i.demomedical.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String usernameField ) throws
            UsernameNotFoundException {
        UserEntity user = userRepository.findByEmailOrUsername(usernameField , usernameField);
        System.out.println(usernameField);
        System.out.println(user);
        if(user == null) {
            throw new UsernameNotFoundException("No user named " + usernameField);
        } else {
            return new UserDetailsImpl(user);
        }
    }

}
