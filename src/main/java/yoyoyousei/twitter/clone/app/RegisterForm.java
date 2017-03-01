package yoyoyousei.twitter.clone.app;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by s-sumi on 2017/03/01.
 */
public class RegisterForm {
    @Size(min = 4,max = 20,message = "ユーザIDは4文字以上20文字以下です")
    @Pattern(regexp = "[a-zA-Z0-9]*",message = "アルファベットまたは数字のみ使用できます")
    private String userId;

    @Size(min = 4,max = 20,message = "パスワードは4文字以上20文字以下です")
    @Pattern(regexp = "[a-zA-Z0-9]*",message = "アルファベットまたは数字のみ使用できます")
    private String password;

    @Size(max = 25,message = "ハンドルネームは25文字以下です")
    private String screenName;


    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
