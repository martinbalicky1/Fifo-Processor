package org.mb.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mb.entity.User;
import org.mb.repository.UserRepository;
import org.mb.service.UserService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @Mock
    private UserRepository mockUserRepository;
    private UserService userService;

    private static final List<User> userTestData = List.of(new User(1, "a1", "MartinTest")
            , new User(2, "a2", "RobertTest"));

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(mockUserRepository);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    void addUserTest() {
        // Given
        var user = userTestData.get(0);

        // When
        when(mockUserRepository.save(user)).thenReturn(user);
        userService.addUser(user);

        // Then
        verify(mockUserRepository).save(user);
    }

    @Test
    void printAllUsersTest() {
        // Given
        var expected = "Users stored in DB: [User[userId=1, " +
                "userGuid=a1, userName=MartinTest], " +
                "User[userId=2, userGuid=a2," +
                " userName=RobertTest]]";

        // When
        when(mockUserRepository.findAll()).thenReturn(userTestData);
        userService.printAllUsers();

        // Then
        verify(mockUserRepository).findAll();
        assertTrue(outContent.toString().contains(expected));
    }

    @Test
    void deleteAllUsersTest() {
        // When
        userService.deleteAllUsers();

        // Then
        verify(mockUserRepository).deleteAll();
        assertTrue(outContent.toString().contains("Users stored in DB to be deleted"));
    }
}