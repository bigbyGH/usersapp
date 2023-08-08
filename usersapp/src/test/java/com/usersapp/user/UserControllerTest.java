package com.usersapp.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usersapp.error.ErrorDTO;
import com.usersapp.error.GlobalExceptionHandler;
import com.usersapp.error.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void whenGetByExistingId_thenReturnOkResponse() throws Exception {
        UUID testId = UUID.randomUUID();

        UserDTO userDTO = new UserDTO()
                .uuid(testId)
                .name("TestName")
                .surname("TestSurname")
                .email("test@useremail.test");

        when(userService.getUserById(any()))
                .thenReturn(userDTO);

        mockMvc.perform(get("/users/" + testId))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.uuid").value(userDTO.getUuid()))
                .andExpect(jsonPath("$.name").value(userDTO.getName()))
                .andExpect(jsonPath("$.surname").value(userDTO.getSurname()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()));
    }

    @Test
    void whenGetByNotExistingId_thenReturnError() throws Exception {
        UUID testId = UUID.randomUUID();
        String errorMessage = "User [UUID = " + testId + " ] not found";

        ErrorDTO errorDTO = new ErrorDTO()
                .message(errorMessage);


        when(userService.getUserById(any()))
                .thenThrow(new ResourceNotFoundException(errorMessage));

        mockMvc.perform(get("/users/" + testId))
                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$.stamp").exists())
                .andExpect(jsonPath("$.applicationName").exists())
                .andExpect(jsonPath("$.message").value(errorDTO.getMessage()));
    }

    @Test
    void whenPostUser_thenReturnOkResponse() throws Exception {

        UUID testId = UUID.randomUUID();

        UserDTO userDTO = new UserDTO()
                .uuid(testId)
                .name("TestName")
                .surname("TestSurname")
                .email("test@useremail.test")
                .password("TestPassword");

        UserDTO savedUserDTO = new UserDTO()
                .uuid(UUID.fromString(userDTO.getUuid()))
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .email(userDTO.getEmail());

        when(userService.createUser(any()))
                .thenReturn(savedUserDTO);

        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDTO))
                )
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.uuid").value(savedUserDTO.getUuid()))
                .andExpect(jsonPath("$.name").value(savedUserDTO.getName()))
                .andExpect(jsonPath("$.surname").value(savedUserDTO.getSurname()))
                .andExpect(jsonPath("$.email").value(savedUserDTO.getEmail()))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void whenPostUserUsingEmptyRequestBody_thenReturnError() throws Exception {
        String errorMessage = "POST request body cannot be null";

        when(userService.createUser(isNull())).thenReturn(null);

        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPutUserUsingExistingId_thenReturnOkResponse() throws Exception {

        UUID testId = UUID.randomUUID();

        UserDTO userDTO = new UserDTO()
                .uuid(testId)
                .name("TestName")
                .surname("TestSurname")
                .email("test@useremail.test")
                .password("TestPassword");

        UserDTO updatedUserDTO = new UserDTO()
                .uuid(UUID.fromString(userDTO.getUuid()))
                .name(userDTO.getName() + "Updated")
                .surname(userDTO.getSurname() + "Updated")
                .email(userDTO.getEmail() + "Updated");

        when(userService.updateUserById(any(), any()))
                .thenReturn(updatedUserDTO);

        mockMvc.perform(
                        put("/users/" + testId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDTO))
                )
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.uuid").value(userDTO.getUuid()))
                .andExpect(jsonPath("$.name").value(updatedUserDTO.getName()))
                .andExpect(jsonPath("$.surname").value(updatedUserDTO.getSurname()))
                .andExpect(jsonPath("$.email").value(updatedUserDTO.getEmail()))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void whenPutUserUsingNotExistingId_thenReturnError() throws Exception {

        UUID testId = UUID.randomUUID();
        String errorMessage = "User [UUID = " + testId + " ] not found";

        ErrorDTO errorDTO = new ErrorDTO()
                .message(errorMessage);

        UserDTO userDTO = new UserDTO()
                .uuid(testId)
                .name("TestName")
                .surname("TestSurname")
                .email("test@useremail.test")
                .password("TestPassword");


        when(userService.updateUserById(any(), any()))
                .thenThrow(new ResourceNotFoundException(errorMessage));


        mockMvc.perform(
                        put("/users/" + testId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDTO))
                )
                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$.stamp").exists())
                .andExpect(jsonPath("$.applicationName").exists())
                .andExpect(jsonPath("$.message").value(errorDTO.getMessage()));
    }

    @Test
    void whenPutUserUsingEmptyRequestBody_thenReturnError() throws Exception {
        String errorMessage = "PUT request body cannot be null";

        when(userService.updateUserById(any(), isNull())).thenReturn(null);

        mockMvc.perform(
                        put("/users/" + UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDeleteUserUsingExistingId_thenReturnNothingAndOkStatus() throws Exception {

        UUID testId = UUID.randomUUID();

        when(userService.deleteUserById(any()))
                .thenReturn(Boolean.TRUE);

        mockMvc.perform(delete("/users/" + testId))
                .andExpect(status().isOk());
    }

    @Test
    void whenDeleteUserUsingNotExistingId_thenReturnError() throws Exception {
        UUID testId = UUID.randomUUID();
        String errorMessage = "User [UUID = " + testId + " ] not found";

        when(userService.deleteUserById(any()))
                .thenThrow(new ResourceNotFoundException(errorMessage));

        mockMvc.perform(delete("/users/" + testId))
                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$.stamp").exists())
                .andExpect(jsonPath("$.applicationName").exists())
                .andExpect(jsonPath("$.message").value(errorMessage));
    }
}