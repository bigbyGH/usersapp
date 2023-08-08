package com.usersapp.user;

import com.usersapp.error.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable("id") UUID uuid)
            throws ResourceNotFoundException {
        return this.userService.getUserById(uuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return this.userService.createUser(userDTO);
    }

    @PutMapping("/{id}")
    public UserDTO updateUserById(@PathVariable("id") UUID uuid,
                                  @RequestBody UserDTO userDTO)
            throws ResourceNotFoundException {
        return this.userService.updateUserById(uuid, userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") UUID uuid)
            throws ResourceNotFoundException {
        this.userService.deleteUserById(uuid);
    }
}
