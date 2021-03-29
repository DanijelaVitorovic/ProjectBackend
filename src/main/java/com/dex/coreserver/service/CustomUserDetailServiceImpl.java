package com.dex.coreserver.service;

import com.dex.coreserver.model.User;
import com.dex.coreserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {
        @Autowired
        UserRepository userRepository;
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username);
            if(user==null) new UsernameNotFoundException("Korisnik nije pronađen!");
            if(user.isAccountNonExpired()) new AccountExpiredException("Istekao nalog!");;
            if(user.isAccountNonLocked()) new LockedException("Nalog je neaktivan!");
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            return user;
        }

        @Transactional
        public User loadUserById(Long id){
            User user = userRepository.getById(id);
            if(user==null) new UsernameNotFoundException("Korisnik nije pronađen!");
            if(user.isAccountNonExpired()) new AccountExpiredException("Istekao nalog!");
            if(user.isAccountNonLocked()) new LockedException("Nalog je neaktivan!");
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            return user;
        }
}
