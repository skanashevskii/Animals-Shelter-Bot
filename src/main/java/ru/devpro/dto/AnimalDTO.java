package ru.devpro.dto;



import org.springframework.stereotype.Component;
import ru.devpro.enums.AnimalType;
import ru.devpro.model.User;

import java.time.LocalDateTime;

@Component
public class AnimalDTO {
        private Long id;

        private String name;

        private AnimalType type_animal;

        private String breed;

        private String text;

        private LocalDateTime dateTime;

        private User user;

    public AnimalDTO() {
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

    public AnimalType getType_animal() {
        return type_animal;
    }

    public void setType_animal(AnimalType type_animal) {
        this.type_animal = type_animal;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
