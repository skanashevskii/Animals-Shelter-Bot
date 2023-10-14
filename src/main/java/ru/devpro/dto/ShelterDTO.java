package ru.devpro.dto;


import org.springframework.stereotype.Component;

@Component

public class ShelterDTO {
    private Long id;
    private String name;
    private ShelterLocationDTO shelterLocation;

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

    public ShelterLocationDTO getShelterLocation() {
        return shelterLocation;
    }

    public void setShelterLocation(ShelterLocationDTO shelterLocation) {
        this.shelterLocation = shelterLocation;
    }
}
