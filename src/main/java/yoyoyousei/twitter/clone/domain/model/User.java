package yoyoyousei.twitter.clone.domain.model;

import yoyoyousei.twitter.clone.util.Util;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


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

    //双方向ならmappedbyが必要で、どのプロパティと関連するのか指定する必要がある。
    @OneToMany(mappedBy = "tweetUser")
    private List<Tweet> tweets;

    //fetchtype: Eager フィールドの呼び出しを最初の呼び出しで行う  lazy:フィールドにアクセスが合った時点で
    //cascade: このプロパティをどのように変更した際に関連するentityに変更を反映するか
    //persist:新規保存 merge:更新 remove:削除 refresh:再取得したとき detatch:永続性コンテキストの管理外になったとき all:全て
    //cascade={hoge,fuga}と複数指定できる
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "relation",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "following_id"))
    private List<User> following;

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

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!getUserId().equals(user.getUserId())) return false;
        return getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getUserId().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }
}
