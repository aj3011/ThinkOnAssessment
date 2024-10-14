package com.thinkon.userapp.user_app.services;

import com.thinkon.userapp.user_app.model.UserInfo;
import com.thinkon.userapp.user_app.respository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService {

    @Autowired
    private UserRepo repo;

    public List<UserInfo> getUserList() {
        List<UserInfo> users = repo.findAll();
        return users;
    }

    public void createUser(final UserInfo userInfo) {
        if (repo.existsByEmail(userInfo.getEmail())) {
            throw new IllegalArgumentException("A user with this email already exists.");
        }

        if (repo.existsByPhoneNumber(userInfo.getPhoneNumber())) {
            throw new IllegalArgumentException("A user with this phone number already exists.");
        }

        repo.save(userInfo);
    }

    public Optional<UserInfo> getUserById(Integer id) {
        return repo.findById(id);
    }

    public boolean deleteUserById(Integer id) {
        Optional<UserInfo> user = repo.findById(id);
        if (user.isPresent()) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Optional<UserInfo> updateUser(Integer id, UserInfo newUserInfo) {
        return repo.findById(id).map(existingUser -> {
            // Check and update only if the new value is not null or empty
            if (newUserInfo.getFirstName() != null && !newUserInfo.getFirstName().isEmpty()) {
                existingUser.setFirstName(newUserInfo.getFirstName());
            }
            if (newUserInfo.getLastName() != null && !newUserInfo.getLastName().isEmpty()) {
                existingUser.setLastName(newUserInfo.getLastName());
            }
            if (newUserInfo.getEmail() != null && !newUserInfo.getEmail().isEmpty()) {
                existingUser.setEmail(newUserInfo.getEmail());
            }
            if (newUserInfo.getPhoneNumber() != null && !newUserInfo.getPhoneNumber().isEmpty()) {
                existingUser.setPhoneNumber(newUserInfo.getPhoneNumber());
            }
            if (newUserInfo.getUsername() != null && !newUserInfo.getUsername().isEmpty()) {
                existingUser.setUsername(newUserInfo.getUsername());
            }

            UserInfo updatedUser = repo.save(existingUser);
            return updatedUser;
        });
    }


}
