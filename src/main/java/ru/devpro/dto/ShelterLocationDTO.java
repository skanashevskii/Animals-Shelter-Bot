package ru.devpro.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.devpro.model.Shelter;

import java.time.LocalDateTime;

@Component

public class ShelterLocationDTO {
    private Long id;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private LocalDateTime dateTime;
    @JsonIgnore
    private Shelter shelter;

    public ShelterLocationDTO() {
    }

    public ShelterLocationDTO(Long id, String address, String city,
                              String state, String zipcode, LocalDateTime dateTime,
                              Shelter shelter) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.dateTime = dateTime;
        this.shelter = shelter;
    }
    // Геттеры и сеттеры для полей


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }
}
