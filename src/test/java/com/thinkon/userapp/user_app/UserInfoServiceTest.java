package com.thinkon.userapp.user_app;

import com.thinkon.userapp.user_app.model.UserInfo;
import com.thinkon.userapp.user_app.respository.UserRepo;
import com.thinkon.userapp.user_app.services.UserInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserInfoServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserInfoService userInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldCreateUser_WhenNoDuplicate() {
        // Arrange
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("test@example.com");
        userInfo.setPhoneNumber("1234567890");

        when(userRepo.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepo.existsByPhoneNumber("1234567890")).thenReturn(false);
        when(userRepo.save(userInfo)).thenReturn(userInfo);

        // Act
        assertDoesNotThrow(() -> userInfoService.createUser(userInfo));

        // Assert
        verify(userRepo, times(1)).save(userInfo);
    }

    @Test
    void createUser_ShouldThrowException_WhenEmailExists() {
        // Arrange
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("test@example.com");
        userInfo.setPhoneNumber("1234567890");

        when(userRepo.existsByEmail("test@example.com")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userInfoService.createUser(userInfo);
        });

        assertEquals("A user with this email already exists.", exception.getMessage());
        //verify save is never called as validation failed.
        verify(userRepo, never()).save(userInfo);
    }

    @Test
    void createUser_ShouldThrowException_WhenPhoneNumberExists() {
        // Arrange
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("test@example.com");
        userInfo.setPhoneNumber("1234567890");

        when(userRepo.existsByPhoneNumber("1234567890")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userInfoService.createUser(userInfo);
        });

        assertEquals("A user with this phone number already exists.", exception.getMessage());
        //verify save is never called as validation failed.
        verify(userRepo, never()).save(userInfo);
    }
}
