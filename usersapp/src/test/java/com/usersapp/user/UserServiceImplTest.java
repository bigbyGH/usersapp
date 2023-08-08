package com.usersapp.user;

import com.usersapp.error.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void whenConvertEntityToDTO_thenPasswordIsNull() {
        User user = new User();
        user.setUuid(UUID.randomUUID());
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setPassword("TestPassword");
        user.setEmail("test@useremail.test");

        UserDTO userDTO = this.userService.convertToDTO(user);

        assertNull(userDTO.getPassword());

        assertNotNull(userDTO.getName());
        assertNotNull(userDTO.getSurname());
        assertNotNull(userDTO.getEmail());
    }

    @Test
    void whenConvertDTOtoEntity_thenPasswordIsNotNull() {
        UserDTO userDTO = new UserDTO()
                .name("TestName")
                .surname("TestSurname")
                .password("TestPassword")
                .email("test@useremail.test");

        User user = this.userService.convertToEntity(userDTO);

        assertNotNull(user.getPassword());

        assertNotNull(user.getName());
        assertNotNull(user.getSurname());
        assertNotNull(user.getEmail());
    }

    @Test
    void whenFindUserByExistingID_returnUser() throws ResourceNotFoundException {
        UUID testId = UUID.randomUUID();

        User user = new User();
        user.setUuid(testId);
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setPassword("TestPassword");
        user.setEmail("test@useremail.test");

        when(userRepository.findById(testId))
                .thenReturn(Optional.of(user));

        UserDTO foundUser = this.userService.getUserById(testId);

        assertEquals(
                user.getUuid().toString(),
                foundUser.getUuid());

        assertEquals(
                user.getName(),
                foundUser.getName());
    }

    @Test
    void whenFindUserByNotExistingID_throwResourceNotFoundException() {
        UUID testId = UUID.randomUUID();

        assertThrows(
                ResourceNotFoundException.class,
                () -> this.userService.getUserById(testId));
    }

    @Test
    void whenUpdateUser_returnUpdatedDTOWithoutPassword() throws ResourceNotFoundException {
        UUID testId = UUID.randomUUID();

        User user = new User();
        user.setUuid(testId);
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setPassword("TestPassword");
        user.setEmail("test@useremail.test");

        UserDTO userDTO = new UserDTO()
                .name("UpdatedName")
                .surname("UpdatedSurname")
                .password("UpdatedPassword")
                .email("UpdatedEmail");

        when(userRepository.findById(testId))
                .thenReturn(Optional.of(user));

        when(userRepository.save(notNull()))
                .thenReturn(user);

        UserDTO savedUser = this.userService.updateUserById(testId, userDTO);

        assertEquals(
                userDTO.getName(),
                savedUser.getName());
        assertEquals(
                userDTO.getSurname(),
                savedUser.getSurname());
        assertEquals(
                userDTO.getEmail(),
                savedUser.getEmail());

        assertNull(savedUser.getPassword());
    }

    @Test
    void whenCreateUser_saveUserAndReturnDTOWithoutPassword() {
        UUID testId = UUID.randomUUID();

        User user = new User();
        user.setUuid(testId);
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setPassword("TestPassword");
        user.setEmail("test@useremail.test");

        UserDTO userDTO = new UserDTO()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(user.getPassword());

        when(userRepository.save(any()))
                .thenReturn(user);

        UserDTO savedUser = this.userService.createUser(userDTO);

        assertEquals(userDTO.getName(), savedUser.getName());
        assertEquals(userDTO.getSurname(), savedUser.getSurname());
        assertEquals(userDTO.getEmail(), savedUser.getEmail());

        assertNull(savedUser.getPassword());
    }

    @Test
    void whenCreateUserAndRequestBodyIsNull_returnNull() {
        UserDTO result = this.userService.createUser(null);

        assertNull(result);
    }

    @Test
    void whenDeleteUserByExistingId_returnTrue() throws ResourceNotFoundException {
        UUID testId = UUID.randomUUID();

        User user = new User();

        user.setUuid(testId);

        when(userRepository.findById(testId))
                .thenReturn(Optional.of(user));

        boolean result = this.userService.deleteUserById(testId);

        assertTrue(result);
    }

    @Test
    void whenDeleteUserByNotExistingId_throwResourceNotFoundException() {
        UUID testId = UUID.randomUUID();

        assertThrows(
                ResourceNotFoundException.class,
                () -> this.userService.deleteUserById(testId));
    }

}