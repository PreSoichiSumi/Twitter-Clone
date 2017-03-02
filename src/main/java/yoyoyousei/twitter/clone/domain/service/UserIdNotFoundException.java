package yoyoyousei.twitter.clone.domain.service;

/**
 * Created by s-sumi on 2017/03/02.
 */
public class UserIdNotFoundException extends RuntimeException {
    public UserIdNotFoundException(String message) {
        super(message);
    }
}
