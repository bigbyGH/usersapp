package com.usersapp.user;

import com.usersapp.error.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * This class consists of methods allowing to save, retrieve, update and delete User data. It implements
 * UserService interface and depends on UserRepository.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get UserDTO data using UUID
     *
     * @param uuid
     * @return UserDTO converted from User entity
     * @throws ResourceNotFoundException if UUID was not found
     */
    @Override
    public UserDTO getUserById(UUID uuid) throws ResourceNotFoundException {
        return this.convertToDTO(
                this.findByIdOrThrowNotFound(uuid)
        );
    }

    /**
     * Update User resource using UserDTO object and UUID.
     *
     * @param uuid of the User resource
     * @param userDTO resource data
     * @return UserDTO converted from saved User entity
     * @throws ResourceNotFoundException if UUID was not found
     */
    @Override
    public UserDTO updateUserById(UUID uuid, final UserDTO userDTO)
            throws ResourceNotFoundException {

        if (isNull(userDTO))
            return null;

        User userToUpdate = this.findByIdOrThrowNotFound(uuid);

        userToUpdate.setName(userDTO.getName());
        userToUpdate.setSurname(userDTO.getSurname());
        userToUpdate.setEmail(userDTO.getEmail());

        if (isNotNull(userDTO.getPassword()))
            userToUpdate.setPassword(userDTO.getPassword());

        return this.convertToDTO(
                this.userRepository.save(userToUpdate)
        );
    }

    /**
     * Create User with UserDTO
     *
     * @param userDTO data for new User resource
     * @return UserDTO converted from new User resource
     */
    @Override
    public UserDTO createUser(final UserDTO userDTO) {

        if (isNull(userDTO))
            return null;

        User newUser = this.convertToEntity(userDTO);

        return this.convertToDTO(
                this.userRepository.save(newUser)
        );
    }

    /**
     * Delete User resource using UUID.
     *
     * @param uuid of the resource to be deleted
     * @return boolean true for success
     * @throws ResourceNotFoundException if the UUID is not present in the db
     */
    @Override
    public boolean deleteUserById(UUID uuid) throws ResourceNotFoundException {

        this.findByIdOrThrowNotFound(uuid);

        this.userRepository.deleteById(uuid);

        return true;
    }

    /**
     * Helper method used to convert a DTO to entity
     *
     * @param userDTO to be used to create User entity object
     * @return User entity object
     */
    @Override
    public User convertToEntity(final UserDTO userDTO) {
        if (isNull(userDTO))
            return null;

        User user = this.getEntity();

        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());

        if (isNotNull(userDTO.getPassword()))
            user.setPassword(userDTO.getPassword());

        return user;
    }

    /**
     * Helper method used to convert an entity to DTO
     *
     * @param user to be used to create UserDTO object
     * @return UserDTO object
     */
    @Override
    public UserDTO convertToDTO(final User user) {

        return this.getDTO()
                .uuid(user.getUuid())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .created_at(
                        isNotNull(user.getCreatedAt()) ? user.getCreatedAt().toString() : null)
                .updated_at(
                        isNotNull(user.getUpdatedAt()) ? user.getUpdatedAt().toString() : null);
    }

    /**
     * Helper method used to retrieve User data from the database using UUID.
     * Will throw an exception if UUID was not found.
     *
     * @param uuid to be found
     * @return User entity of the specified UUID
     * @throws ResourceNotFoundException if the UUID was not found
     */
    private User findByIdOrThrowNotFound(UUID uuid) throws ResourceNotFoundException {
        return this.userRepository.findById(uuid)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User [UUID = " + uuid + " ] not found"));
    }

    /**
     * Creates User entity object
     *
     * @return blank User object
     */
    private User getEntity() {
        return new User();
    }

    /**
     * Creates UserDTO object
     *
     * @return blank UserDTO object
     */
    private UserDTO getDTO() {
        return new UserDTO();
    }

    /**
     * Helper method to check whether the object is null.
     *
     * @param object to be checked
     * @return boolean value; true if the object is null
     */
    private boolean isNull(Object object) {
        return object == null;
    }

    /**
     * Helper method to check whether the object is not null.
     *
     * @param object to be checked
     * @return boolean value; true if the object is not null
     */
    private boolean isNotNull(Object object) {
        return object != null;
    }
}