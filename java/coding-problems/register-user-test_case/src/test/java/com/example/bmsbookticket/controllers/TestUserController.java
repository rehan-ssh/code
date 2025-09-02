package com.example.bmsbookticket.controllers;

import com.example.bmsbookticket.dtos.*;
import com.example.bmsbookticket.models.User;
import com.example.bmsbookticket.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @Test
    public void testSignupUserSuccess() {
        SignupUserRequestDTO requestDTO = new SignupUserRequestDTO();
        String email = "test@scaler.com";
        String password = "Password@123";
        String name = "Test User";
        requestDTO.setEmail(email);
        requestDTO.setName(name);
        requestDTO.setPassword(password);

        SignupUserResponseDTO signupUserResponseDTO = userController.signupUser(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, signupUserResponseDTO.getResponseStatus());
        assertEquals(email, signupUserResponseDTO.getEmail(), "Email should match");
        assertEquals(name, signupUserResponseDTO.getName(), "Name should match");
        User user = userRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(user);
        assertEquals(email, user.getEmail(), "Email should match");
        assertEquals(name, user.getName(), "Name should match");
        assertNotNull(user.getPassword(), "Password should not be null");
        assertNotEquals(password, user.getPassword(), "Password should be encrypted");
    }

    @Test
    public void testSignupUser_SameUser_RegisteringTwice_Failure() {
        SignupUserRequestDTO requestDTO = new SignupUserRequestDTO();
        String email = "test@scaler.com";
        String password = "Password@123";
        String name = "Test User";
        requestDTO.setEmail(email);
        requestDTO.setName(name);
        requestDTO.setPassword(password);

        SignupUserResponseDTO signupUserResponseDTO = userController.signupUser(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, signupUserResponseDTO.getResponseStatus(), "Response status should be SUCCESS");
        assertEquals(email, signupUserResponseDTO.getEmail(), "Email should match");
        assertEquals(name, signupUserResponseDTO.getName(), "Name should match");

        signupUserResponseDTO = userController.signupUser(requestDTO);
        assertEquals(ResponseStatus.FAILURE, signupUserResponseDTO.getResponseStatus(), "Response status should be FAILURE");
        assertNull(signupUserResponseDTO.getEmail(), "Email should be null");
        assertNull(signupUserResponseDTO.getName(), "Name should be null");
    }

    @Test
    public void testSignupUserSuccess_AndLoginSuccess() {
        SignupUserRequestDTO requestDTO = new SignupUserRequestDTO();
        String email = "test@scaler.com";
        String password = "Password@123";
        String name = "Test User";
        requestDTO.setEmail(email);
        requestDTO.setName(name);
        requestDTO.setPassword(password);

        SignupUserResponseDTO signupUserResponseDTO = userController.signupUser(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, signupUserResponseDTO.getResponseStatus());
        assertEquals(email, signupUserResponseDTO.getEmail());
        assertEquals(name, signupUserResponseDTO.getName());

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail(email);
        loginRequestDto.setPassword(password);
        LoginResponseDto loginResponseDto = userController.login(loginRequestDto);
        assertTrue(loginResponseDto.isLoggedIn());
        assertEquals(ResponseStatus.SUCCESS, loginResponseDto.getResponseStatus());
    }

    @Test
    public void testSignupUserSuccess_AndLoginWithIncorrectPassword() {
        SignupUserRequestDTO requestDTO = new SignupUserRequestDTO();
        String email = "test@scaler.com";
        String password = "Password@123";
        String name = "Test User";
        requestDTO.setEmail(email);
        requestDTO.setName(name);
        requestDTO.setPassword(password);

        SignupUserResponseDTO signupUserResponseDTO = userController.signupUser(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, signupUserResponseDTO.getResponseStatus());
        assertEquals(email, signupUserResponseDTO.getEmail());
        assertEquals(name, signupUserResponseDTO.getName());

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail(email);
        loginRequestDto.setPassword(password + "1");
        LoginResponseDto loginResponseDto = userController.login(loginRequestDto);
        assertFalse(loginResponseDto.isLoggedIn());
        assertEquals(ResponseStatus.SUCCESS, loginResponseDto.getResponseStatus());
    }

    @Test
    public void testSignupUser_UserAlreadyExists() {
        SignupUserRequestDTO requestDTO = new SignupUserRequestDTO();
        String email = "test@scaler.com";
        String password = "Password@123";
        String name = "Test User";
        requestDTO.setEmail(email);
        requestDTO.setName(name);
        requestDTO.setPassword(password);

        //User registering for the first time
        SignupUserResponseDTO signupUserResponseDTO = userController.signupUser(requestDTO);

        //User registering for the second time
        signupUserResponseDTO = userController.signupUser(requestDTO);
        assertEquals(ResponseStatus.FAILURE, signupUserResponseDTO.getResponseStatus());
        assertNull(signupUserResponseDTO.getEmail());
        assertNull(signupUserResponseDTO.getName());
    }

    @Test
    public void testLogin_UserNotSignedUp() {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        String email = "test@scaler.com";
        String password = "Password@123";
        loginRequestDto.setEmail(email);
        loginRequestDto.setPassword(password);

        LoginResponseDto loginResponseDto = userController.login(loginRequestDto);
        assertFalse(loginResponseDto.isLoggedIn());
        assertEquals(ResponseStatus.FAILURE, loginResponseDto.getResponseStatus());
    }
}
