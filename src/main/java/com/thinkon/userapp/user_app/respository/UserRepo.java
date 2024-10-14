package com.thinkon.userapp.user_app.respository;

import com.thinkon.userapp.user_app.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//takes in model and primary key datatype
@Repository
public interface UserRepo extends JpaRepository<UserInfo, Integer> {

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
