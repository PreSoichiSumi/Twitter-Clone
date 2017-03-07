package yoyoyousei.twitter.clone.domain.model;

import yoyoyousei.twitter.clone.util.Util;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


//デフォルトコンストラクタが必要
@Entity
//@Table(name = "usr")
public class User{

    /*@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;*/

    @Id
    @NotNull
    //@Column(unique = true)
    private String userId;

    @NotNull
    private String password;

    @NotNull
    private String screenName;

    @NotNull
    private RoleName roleName;

    private String biography;

    private String iconPath;

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public User(String userId, String password, String screenName) {
        this.userId = userId;
        this.password = password;
        this.screenName = (screenName == null || screenName.equals("")) ?
                "no name" : screenName;
        this.roleName = RoleName.USER;
        this.iconPath = Util.getNoIcon();
    }

    public User() {
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
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

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", screenName='" + screenName + '\'' +
                '}';
    }
}
