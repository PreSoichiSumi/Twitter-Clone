package yoyoyousei.twitter.clone.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yoyoyousei.twitter.clone.domain.model.User;
import yoyoyousei.twitter.clone.domain.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User create(User user) throws UserIdAlreadyExistsException{
        User tmp=userRepository.findOne(user.getUserId());
        if(tmp!=null)
            throw new UserIdAlreadyExistsException(user.getUserId()+" is already exists.");
        return userRepository.save(user);
    }
    public User update(User user) {
        if(!userRepository.exists(user.getUserId()))
            throw new UserIdNotFoundException(user.getUserId() +"is not found.");

        userRepository.save(user);
        return user;
    }
    public void delete(String id){
        userRepository.delete(id);
    }
    public User find(String id){
        return userRepository.findOne(id);
    }
}
