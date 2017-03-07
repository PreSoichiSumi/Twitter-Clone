package yoyoyousei.twitter.clone.domain.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by s-sumi on 2017/02/27.
 */
@Entity
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tweetId;

    @CreationTimestamp  //@prepersistしてpostTime=new Timestamp(sys.currentTimeMilis());と同義
                        //updatetimestampもある
    private Timestamp postTime;

    @ManyToOne
    private User tweetUser;

    @NotNull
    private String content;

    public User getTweetUser() {
        return tweetUser;
    }

    public void setTweetUser(User tweetUser) {
        this.tweetUser = tweetUser;
    }

    public Tweet() {
    }

    public Tweet(String content , User tweetUser) {
        this.content = content;
        this.tweetUser=tweetUser;
    }

    public Integer getTweetId() {
        return tweetId;
    }

    public void setTweetId(Integer tweetId) {
        this.tweetId = tweetId;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
