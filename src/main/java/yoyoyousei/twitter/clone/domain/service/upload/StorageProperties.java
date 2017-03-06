package yoyoyousei.twitter.clone.domain.service.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by s-sumi on 2017/03/05.
 */
@ConfigurationProperties("storage")
public class StorageProperties {
    private String uploadedFileLocation="upload-dir";

    public String getUploadedFileLocation() {
        return uploadedFileLocation;
    }

    public void setUploadedFileLocation(String uploadedFileLocation) {
        this.uploadedFileLocation = uploadedFileLocation;
    }
}
