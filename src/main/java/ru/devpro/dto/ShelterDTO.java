package ru.devpro.dto;


import org.springframework.stereotype.Component;
import ru.devpro.model.ShelterLocation;

import java.time.LocalDateTime;

@Component

public class ShelterDTO {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private ShelterLocation shelterLocation;

    public ShelterDTO() {
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
