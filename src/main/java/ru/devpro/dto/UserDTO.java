package ru.devpro.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;
import ru.devpro.enums.AccessLevel;
import ru.devpro.model.Animal;
import ru.devpro.model.Shelter;

import java.time.LocalDateTime;
import java.util.Set;

@Component
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
    @JsonIgnore
    private Shelter shelter;
    @JsonIgnore
    private Set<Animal> animals;

    public UserDTO() {
    }

    public UserDTO(Long id, Long chatId, String name, String family, String role,
                   String telephone, String email, LocalDateTime dateTime,
                   AccessLevel accessLevel, Shelter shelter, Set<Animal> animals) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.family = family;
        this.role = role;
        this.telephone = telephone;
        this.email = email;
        this.dateTime = dateTime;
        this.accessLevel = accessLevel;
        this.shelter = shelter;
        this.animals = animals;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }
}
