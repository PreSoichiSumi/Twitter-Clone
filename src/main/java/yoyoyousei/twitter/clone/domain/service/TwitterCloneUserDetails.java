package yoyoyousei.twitter.clone.domain.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import yoyoyousei.twitter.clone.domain.model.User;

import java.util.Collection;

/**
 * Created by s-sumi on 2017/02/28.
 */
public class TwitterCloneUserDetails implements UserDetails {
    private final User user;

    public TwitterCloneUserDetails(User user) {
        this.user = user;
    }
    public User getuser(){
        return user;
    }

    //定形
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return AuthorityUtils.createAuthorityList("ROLE_"+this.user.getRoleName().name());
    }

    //認証に使うpassword
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    //認証に使うuserId
    @Override
    public String getUsername() {
        return this.user.getUserId();
    }

    //アカウント期限切れ機能
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //アカウントロック機能
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //パスワード有効期限切れ機能
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //アカウント無効化の機能
    @Override
    public boolean isEnabled() {
        return true;
    }
}
