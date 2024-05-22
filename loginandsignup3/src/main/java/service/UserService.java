package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entitiy.User;
import repository.PrimaryUserRepository;
import repository.SecondaryUserRepository;

@Service
public class UserService {

    @Autowired
    private PrimaryUserRepository primaryUserRepository;

    @Autowired
    private SecondaryUserRepository secondaryUserRepository;

    @Transactional("primaryTransactionManager")
    public void saveUserToPrimary(User user) {
        primaryUserRepository.save(user);
    }

    @Transactional("secondaryTransactionManager")
    public void saveUserToSecondary(User user) {
        secondaryUserRepository.save(user);
    }

    public User findByUsername(String username) {
        User user = primaryUserRepository.findByUsername(username);
        if (user == null) {
            user = secondaryUserRepository.findByUsername(username);
        }
        return user;
    }

    public void saveUser(User user) {
        try {
            saveUserToPrimary(user);
        } catch (Exception e) {
            
             e.printStackTrace();
        }
        try {
            saveUserToSecondary(user);
        } catch (Exception e) {
           
             e.printStackTrace();
        }
    }
}


