package ru.devpro.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;
import ru.devpro.model.ShelterLocation;

import java.time.LocalDateTime;

@Component

public class ShelterDTO {
    private Long id;
    private String name;
    private String safety;
    private LocalDateTime dateTime;
    @JsonIgnore
    private ShelterLocation shelterLocation;

    public ShelterDTO() {
    }

    public ShelterDTO(Long id, String name, String safety, LocalDateTime dateTime,
                      ShelterLocation shelterLocation) {
        this.id = id;
        this.name = name;
        this.safety = safety;
        this.dateTime = dateTime;
        this.shelterLocation = shelterLocation;
    }

    public String getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        this.safety = safety;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ShelterLocation getShelterLocation() {
        return shelterLocation;
    }

    public void setShelterLocation(ShelterLocation shelterLocation) {
        this.shelterLocation = shelterLocation;
    }
}
