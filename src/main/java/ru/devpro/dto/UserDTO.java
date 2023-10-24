package ru.devpro.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import ru.devpro.enums.AccessLevel;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO {

    private Long id;
    private Long chatId;
    private String name;
    private String family;
    private String role;
    private String telephone;
    private String email;
    private LocalDateTime dateTime;
    private AccessLevel accessLevel;
    //@JsonIgnore
    private ShelterDTO shelterDTO;
    //@JsonIgnore
    private Set<AnimalDTO> animalDTOs;
    //@JsonIgnore
    private List<ReportDTO> reportDTOs;



}
