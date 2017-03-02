package yoyoyousei.twitter.clone.app;

import javax.validation.constraints.Size;

/**
 * Created by s-sumi on 2017/02/28.
 */
public class UserForm {
    @Size(max = 25,message = "ハンドルネームは25文字以下です")
    private String screenName;

    private String biography;

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
