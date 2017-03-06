package yoyoyousei.twitter.clone.domain.service.upload;

/**
 * Created by s-sumi on 2017/03/05.
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
