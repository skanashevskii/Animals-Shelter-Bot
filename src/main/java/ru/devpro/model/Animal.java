package ru.devpro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.devpro.enums.AnimalType;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Кличка животного")
    @NotNull
    private String name;
    @Schema(description = "Вид животного(CAT/DOG)")
    @Enumerated(EnumType.STRING)
    @Column(name = "type_animal")
    private AnimalType type_animal;
    @Schema(description = "Порода животного")
    @NotBlank
    private String breed;
    @Schema(description = "Описание животного")
    @NotBlank
    private String text;
    @Column(name="animal_time", nullable = false)
    private LocalDateTime dateTime;


    @ManyToOne
    @JoinColumn(name = "user_id")
    //@JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public Animal() {
    }

    public Animal(Long id, String name, AnimalType type_animal,
                  String breed, String text, LocalDateTime dateTime, User user) {
        this.id = id;
        this.name = name;
        this.type_animal = type_animal;
        this.breed = breed;
        this.text = text;
        this.dateTime = dateTime;
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type_animal='" + type_animal + '\'' +
                ", breed='" + breed + '\'' +
                ", text='" + text + '\'' +
                ", dateTime=" + dateTime +
                ", user=" + user +
                '}';
    }
}
