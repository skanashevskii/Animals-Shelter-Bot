package controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.devpro.controllers.UsersController;
import ru.devpro.dto.UserDTO;
import ru.devpro.service.UserService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UsersControllerTest {

    @InjectMocks
    private UsersController usersController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO();
        when(userService.createUser(userDTO)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = usersController.createUser(userDTO);

        verify(userService, times(1)).createUser(userDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testEditUser() {
        UserDTO userDTO = new UserDTO();
        when(userService.editUser(userDTO)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = usersController.editUser(userDTO);

        verify(userService, times(1)).editUser(userDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        when(userService.findUserById(userId)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = usersController.getUserById(userId);

        verify(userService, times(1)).findUserById(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetUserByIdUserNotFound() {
        Long userId = 1L;
        when(userService.findUserById(userId)).thenReturn(null);

        ResponseEntity<UserDTO> response = usersController.getUserById(userId);

        verify(userService, times(1)).findUserById(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteUser() {
        long userId = 1;

        ResponseEntity<Void> response = usersController.deleteUser(userId);

        verify(userService, times(1)).deleteUserById(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testFindAllUsers() {
        UserDTO[] userArray = new UserDTO[] {new UserDTO(), new UserDTO()};
        when(userService.findAll()).thenReturn(Arrays.asList(userArray));

        ResponseEntity<Collection<UserDTO>> response = usersController.findAllUsers();

        verify(userService, times(1)).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userArray.length, Objects.requireNonNull(response.getBody()).size());
    }
}
