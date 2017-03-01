package yoyoyousei.twitter.clone.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yoyoyousei.twitter.clone.domain.model.User;
import yoyoyousei.twitter.clone.domain.repository.UserRepository;

/**
 * Created by s-sumi on 2017/02/28.
 */
@Service
public class TwitterCloneUserDetailsService implements UserDetailsService{
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findOne(username);
        if(user==null){
            throw new UsernameNotFoundException(username+" is not found.");
        }
        return new TwitterCloneUserDetails(user);
    }
}
