package ru.devpro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "owner")
@Schema(description = "Информация о новом владельце")

public class Owner {

    @Schema(description = "Идентификатор владельца")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Имя владельца")
    @NotEmpty(message = "name cannot be empty")
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    @Schema(description = "Фамилия владельца")
    @NotEmpty(message = "family cannot be empty")
    @Size(min = 1, max = 50)
    @Column(name = "family")
    private String family;
    @Schema(description = "Телефон владельца")
    @NotEmpty(message = "telephone cannot be empty")
    @Size(min = 1, max = 50)
    @Column(name = "telephone")
    private Long telephone;

    @Schema(description = "e-mail владельца")
    @Email(message = "Не правильный Email", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 10, max = 50)
    @Column(name = "e-mail")
    private String email;


    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Cat> cats;
    private Set<Dog> dogs;

    public Owner() {
    }

    public Owner(Long id, String name, String family, Long telephone,
                 String email, Set<Cat> cats, Set<Dog> dogs) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.telephone = telephone;
        this.email = email;
        this.cats = cats;
        this.dogs = dogs;
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

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Cat> getCats() {
        return cats;
    }

    public void setCats(Set<Cat> cats) {
        this.cats = cats;
    }

    public Set<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(Set<Dog> dogs) {
        this.dogs = dogs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(id, owner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", telephone=" + telephone +
                ", email='" + email + '\'' +
                ", cats=" + cats +
                ", dogs=" + dogs +
                '}';
    }
}
