package com.thinkon.userapp.user_app.controller;

import com.thinkon.userapp.user_app.model.UserInfo;
import com.thinkon.userapp.user_app.services.UserInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @GetMapping
    ResponseEntity<List<UserInfo>> getUserList(){
        List<UserInfo> users = userInfoService.getUserList();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfo> getUserById(@PathVariable Integer id) {
        Optional<UserInfo> user = userInfoService.getUserById(id);
        return user.map(ResponseEntity::ok)
                // return HTTP 404 if not found
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody final UserInfo userInfo){
        userInfoService.createUser(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("User Created Successfully");
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserInfoById(@PathVariable Integer id, @Valid @RequestBody final UserInfo userInfo){
        Optional<UserInfo> updatedUser = userInfoService.updateUser(id, userInfo);
        if (updatedUser.isPresent()) {
            return ResponseEntity.ok("User updated successfully");
        } else {
            // HTTP 404 if user doesn't exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with ID " + id + " not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer id){
        boolean isDeleted = userInfoService.deleteUserById(id);
        if (isDeleted) {
            // return HTTP 204 No Content for successful deletion
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            // return HTTP 404 if user doesn't exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with ID " + id + " not found");
        }
    }
}
