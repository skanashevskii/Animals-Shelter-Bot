package ru.devpro.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.devpro.model.ShelterLocation;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ShelterDTO {
    private Long id;
    private String name;
    private String safety;
    private LocalDateTime dateTime;
    @JsonIgnore
    private ShelterLocation shelterLocation;
    private List<ShelterLocation> shelterLocations;


}
