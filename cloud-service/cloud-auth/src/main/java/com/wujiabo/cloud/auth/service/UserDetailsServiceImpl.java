package com.wujiabo.cloud.auth.service;

import com.wujiabo.cloud.auth.dto.UserDetailDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<? extends GrantedAuthority> authorities = null;
        return new UserDetailDto("admin","$2a$10$OO0TQFDBDpw8S8CQTh6c0.S/y.yFlWrxYVv.WCVBMSXrdgAeYoknq",authorities);
    }

}
