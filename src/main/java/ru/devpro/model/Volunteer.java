package ru.devpro.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.Set;

@Schema(description = "Информация о волонтере")
@Entity
@Table(name = "volunteer")
public class Volunteer {
    @Schema(description = "Идентификатор волонтера")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Имя волонтера")
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    @Schema(description = "Телефон волонтера")
    @Size(min = 7, max = 50)
    @Column(name = "telephone")
    private Long telephone;


    @OneToMany(mappedBy = "volunteer",fetch = FetchType.EAGER)
    private Set<Cat> cats;
    @OneToMany(mappedBy = "volunteer",fetch = FetchType.EAGER)
    private Set<Dog> dogs;

    public Volunteer() {
    }

    public Volunteer(Long id, String name, Long telephone) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
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

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
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
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(id, volunteer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", telephone=" + telephone +
                ", cats=" + cats +
                ", dogs=" + dogs +
                '}';
    }
}
