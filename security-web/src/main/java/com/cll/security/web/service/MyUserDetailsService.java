package com.cll.security.web.service;

import com.cll.security.web.entity.User;
import com.cll.security.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author chenliangliang
 * @date 2018/6/2
 */
@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByName(username);
        System.out.println(user);
        if (user==null){
            throw new UsernameNotFoundException(username+" not found");
        }else {

            return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),
                    AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user"));
        }
    }
}
