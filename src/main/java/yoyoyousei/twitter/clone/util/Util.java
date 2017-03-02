package yoyoyousei.twitter.clone.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import yoyoyousei.twitter.clone.domain.model.User;
import yoyoyousei.twitter.clone.domain.service.TwitterCloneUserDetails;

import java.security.Principal;

/**
 * Created by s-sumi on 2017/03/02.
 */
public class Util {
    public static User getUserFromPrincipal(Principal principal){
        Authentication authentication=(Authentication)principal;
        TwitterCloneUserDetails userDetails=TwitterCloneUserDetails.class.cast(authentication.getPrincipal());
        return userDetails.getuser();
        /*TwitterCloneUserDetails userDetails=(TwitterCloneUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userDetails.getuser();*/
    }
    public static void updateAuthenticate(Authentication principal, User newUser) {
        Authentication oldAuth= principal;
        Authentication newAuth=new UsernamePasswordAuthenticationToken(new TwitterCloneUserDetails(newUser),oldAuth.getCredentials(),oldAuth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
