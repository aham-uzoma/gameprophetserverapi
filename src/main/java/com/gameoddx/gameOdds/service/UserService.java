package com.gameoddx.gameOdds.service;

import com.gameoddx.gameOdds.model.User;
import com.gameoddx.gameOdds.repository.PredictableRepository;
import com.gameoddx.gameOdds.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //write CRUD Operations in the service repository

//    public ResponseEntity<User> getUserByEmail(String email) {
//        User userByEmail = userRepository.findById();
//        return ResponseEntity.ok(userByEmail);
//
//    }
      public User createNewUser(User user){
        return userRepository.save(user);
      }
      public User findByEmail(String email){
         return userRepository.findByEmail(email);
      }

}
