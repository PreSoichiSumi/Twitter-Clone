package yoyoyousei.twitter.clone.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoyoyousei.twitter.clone.domain.model.Tweet;

import java.util.List;

/**
 * Created by s-sumi on 2017/02/28.
 */
//モデルのCLUD担当
public interface TweetRepository extends JpaRepository<Tweet,Integer>{
    List<Tweet> findAllByOrderByPostTimeDesc();
    List<Tweet> findTop100ByTweetUser_UserIdInOrderByPostTimeDesc(List<String> tweetUserId);
}
