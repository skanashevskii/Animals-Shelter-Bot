package ru.devpro.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import ru.devpro.enums.AccessLevel;


import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;



@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="chat_id",nullable = false)
    private Long chatId;

    @Schema(description = "Имя")
    @Column(name="name",nullable = false)
    @NotEmpty(message = "name cannot be empty")
    @Size(min = 1, max = 50)
    private String name;
    @Schema(description = "Фамилия")
    @Column(name="family",nullable = false)
    @NotEmpty(message = "family cannot be empty")
    @Size(min = 1, max = 50)
    private String family;

    @Schema(description = "Роль пользователя (owner/volunteer)")
    @Column(name="role",nullable = false)
    @NotEmpty(message = "Role cannot be empty")
    @Size(min = 1, max = 50)
    private String role;

    @Schema(description = "Телефон")
    @Column(name="telephone_number",nullable = false)
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Invalid phone number format")
    @Size(min = 7, max = 15, message = "Phone number must be between 7 and 15 characters")
    private String telephone;
    @Schema(description = "e-mail")
    @Column(name="email",nullable = false)
    @Email(message = "Не правильный Email",
            regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 10, max = 50)
    private String email;
    @Column(name="user_time", nullable = false)
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "access_level")
    private AccessLevel accessLevel;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<Animal> animals;


    public User() {
    }

    public User(Long id, Long chatId, String name, String family, String role,
                String telephone, String email,
                LocalDateTime dateTime, AccessLevel accessLevel, Shelter shelter,
                Set<Animal> animals) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", role='" + role + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", dateTime=" + dateTime +
                ", accessLevel=" + accessLevel +
                ", shelter=" + shelter +
                ", animals=" + animals +
                '}';
    }
}
