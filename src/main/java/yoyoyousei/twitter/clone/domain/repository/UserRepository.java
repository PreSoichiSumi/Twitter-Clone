package yoyoyousei.twitter.clone.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoyoyousei.twitter.clone.domain.model.User;

public interface UserRepository extends JpaRepository<User,String>{
    //User findFirstByUserId(String userId);//他にfindTop10By...が使える
}
