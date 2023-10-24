package ru.devpro.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.devpro.dto.ShelterDTO;
import ru.devpro.repositories.*;
import ru.devpro.service.AnimalService;
import ru.devpro.service.ShelterService;
import ru.devpro.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ShelterController.class)

public class ShelterControllerTestMvcTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShelterController.class);
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShelterRepository shelterRepository;
    @MockBean
    private ShelterLocationRepository shelterLocationRepository;
    @MockBean
    private AnimalsRepository animalsRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private ReportRepository reportRepository;
    @MockBean
    private UsersRepository usersRepository;
    @SpyBean
    private UserService userService;
    @SpyBean
    private ShelterService shelterService;
    @SpyBean
    private AnimalService animalService;
    @InjectMocks
    private UsersController usersController;
    @InjectMocks
    private AnimalsController animalsController;
    @InjectMocks
    private AvatarController avatarController;
    @InjectMocks
    private ShelterController shelterController;


    @Test
    public void testCreateShelter() throws Exception {
        // Создание объекта ShelterDTO для отправки в теле запроса
        ShelterDTO shelterDTO = new ShelterDTO();
        shelterDTO.setName("Shelter Name");


        // Mock (имитация) поведения метода createShelter сервиса
        when(shelterService.createShelter(any(ShelterDTO.class))).thenReturn(shelterDTO);

        // Выполните POST-запрос для создания приюта
        mockMvc.perform(MockMvcRequestBuilders.post("/shelters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(shelterDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Shelter Name"))
                .andExpect(jsonPath("$.location").value("Shelter Location"));
    }

    @Test
    public void testEditShelter() throws Exception {
        // Создание объекта ShelterDTO для отправки в теле запроса
        ShelterDTO shelterDTO = new ShelterDTO();
        shelterDTO.setName("Updated Shelter Name");


        // Mock (имитация) поведения метода editShelter сервиса
        when(shelterService.editShelter(any(ShelterDTO.class))).thenReturn(shelterDTO);

        // Выполните PUT-запрос для изменения приюта
        mockMvc.perform(MockMvcRequestBuilders.put("/shelters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(shelterDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Shelter Name"))
                .andExpect(jsonPath("$.location").value("Updated Shelter Location"));
    }

    @Test
    public void testGetShelterById() throws Exception {
        // Создайте идентификатор приюта
        Long shelterId = 1L;

        // Создайте объект ShelterDTO для имитации возвращения
        ShelterDTO shelterDTO = new ShelterDTO();
        shelterDTO.setId(shelterId);
        shelterDTO.setName("Shelter Name");


        // Mock (имитация) поведения метода findShelterById сервиса
        when(shelterService.findShelterById(shelterId)).thenReturn(shelterDTO);

        // Выполните GET-запрос для получения информации о приюте
        mockMvc.perform(MockMvcRequestBuilders.get("/shelters/" + shelterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Shelter Name"))
                .andExpect(jsonPath("$.location").value("Shelter Location"));
    }

    @Test
    public void testDeleteShelter() throws Exception {
        // Создайте идентификатор приюта
        Long shelterId = 1L;

        // Выполните DELETE-запрос для удаления приюта
        mockMvc.perform(MockMvcRequestBuilders.delete("/shelters/" + shelterId))
                .andExpect(status().isNoContent());
    }

    // Вспомогательная функция для преобразования объекта в JSON
    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}












/*
@WebMvcTest(ShelterController.class)
public class ShelterControllerTestMvcTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShelterController.class);
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShelterRepository shelterRepository;
    @MockBean
    private ShelterLocationRepository shelterLocationRepository;
    @MockBean
    private AnimalsRepository animalsRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private ReportRepository reportRepository;
    @MockBean
    private UsersRepository usersRepository;
    @SpyBean
    private UserService userService;
    @SpyBean
    private ShelterService shelterService;
    @SpyBean
    private AnimalService animalService;
    @InjectMocks
    private UsersController usersController;
    @InjectMocks
    private AnimalsController animalsController;
    @InjectMocks
    private AvatarController avatarController;
    @InjectMocks
    private ShelterController shelterController;

}*/
