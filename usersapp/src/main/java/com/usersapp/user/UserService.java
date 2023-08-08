package com.usersapp.user;

import com.usersapp.error.exception.ResourceNotFoundException;

import java.util.UUID;

/**
 * An interface defining methods to handle User data
 */
public interface UserService {

    UserDTO getUserById(UUID uuid) throws ResourceNotFoundException;

    UserDTO updateUserById(UUID uuid, final UserDTO userDTO) throws ResourceNotFoundException;

    UserDTO createUser(final UserDTO userDTO);

    boolean deleteUserById(UUID uuid) throws ResourceNotFoundException;

    User convertToEntity(final UserDTO userDTO);

    UserDTO convertToDTO(final User user);

}
