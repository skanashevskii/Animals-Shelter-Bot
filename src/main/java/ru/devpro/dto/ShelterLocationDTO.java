package ru.devpro.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.devpro.model.Shelter;

import java.time.LocalDateTime;

@Data
public class ShelterLocationDTO {
    private Long id;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private LocalDateTime dateTime;
    @JsonIgnore
    private Shelter shelter;

}
