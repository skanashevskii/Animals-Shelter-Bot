package ru.devpro.controllers;


import org.json.JSONObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import ru.devpro.dto.UserDTO;
import ru.devpro.model.User;
import ru.devpro.repositories.UsersRepository;
import ru.devpro.service.UserService;


import java.util.Arrays;
import java.util.Collection;

import java.util.Optional;


import static org.mockito.Mockito.*;


@WebMvcTest(UsersController.class)
public class UsersControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsersRepository usersRepository;
    @MockBean
    private UserService userService;

    @InjectMocks
    private UsersController usersController;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() throws Exception{

        final long id = 1;
        final String name = "ivan ivanov";

        LOGGER.info("Received request to save user: name={}", name);
        // Создание объекта JSON
        JSONObject userObject = new JSONObject();
        userObject.put("id", id);
        userObject.put("name", name);


        User user = new User();
        user.setId(id);
        user.setName(name);

        // Mock (имитация) поведения методов save и findById репозитория
        when(usersRepository.save(any(User.class))).thenReturn(user);
        when(usersRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        // Запрос POST, чтобы сохранить учащегося
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

    @Test
    public void testEditUser() throws Exception {
        final long id = 1;
        final String name = "ivan ivanov";

        LOGGER.info("Received request to save user: name={}", name);
        // Create a UserDTO JSON object
        JSONObject userObject = new JSONObject();
        userObject.put("id", id);
        userObject.put("name", name);

        User user = new User();
        user.setId(id);
        user.setName(name);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setName(name);

        when(usersRepository.findById(id)).thenReturn(Optional.of(user)); // Mock the repository to return the User entity
        when(userService.editUser(userDTO)).thenReturn(userDTO); // Mock the service to return the UserDTO

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users")
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void testGetUserById() throws Exception {
        final long id = 1;
        final String name = "ivan ivanov";

        LOGGER.info("Received request to save user: name={}", name);
        // Create a UserDTO JSON object
        JSONObject userObject = new JSONObject();
        userObject.put("id", id);
        userObject.put("name", name);

        User user = new User();
        user.setId(id);
        user.setName(name);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setName(name);
        when(usersRepository.findById(id)).thenReturn(Optional.of(user));
        when(userService.findUserById(id)).thenReturn(userDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Long userId = 1L; // Идентификатор пользователя для удаления
        User user = new User(userId, "ваня");
        usersRepository.save(user);

        // Мокируем операцию удаления пользователя
        Mockito.doAnswer(invocation -> {
            usersRepository.delete(user); // Вместо deleteById удаляем сами пользователя
            return null;
        }).when(usersRepository).deleteById(userId);

        // Выполнение теста
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        User deletedUser = usersRepository.findById(userId).orElse(null);
        Assertions.assertNull(deletedUser);
    }




    @Test
    public void testFindAllUsers() throws Exception {
        // Подготовьте данные для теста
        UserDTO user1 = new UserDTO(1L, "User 1");
        UserDTO user2 = new UserDTO(2L, "User 2");
        UserDTO user3 = new UserDTO(3L, "User 3");
        Collection<UserDTO> users = Arrays.asList(user1, user2, user3);

        // Мокируйте сервис, чтобы он возвращал список пользователей
        Mockito.when(userService.findAll()).thenReturn(users);

        // Выполните запрос на получение всех пользователей
        mockMvc.perform(MockMvcRequestBuilders.get("/users/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(users.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(user1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(user1.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(user2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(user2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(user3.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value(user3.getName()));
    }
}
