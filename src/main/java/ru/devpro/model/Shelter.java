package ru.devpro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "shelter")
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Приют")
    @NotNull
    @Column(name = "name")
    private String name;

    @Schema(description = "Рекомендации")
    @NotNull
    @Column(name="safety_advise")
    private String safety;
    @Column(name="shelter_time", nullable = false)
    private LocalDateTime dateTime;
    @OneToOne
    @JoinColumn(name="shelter_location_id")
    private ShelterLocation shelterLocation;

    @OneToMany(mappedBy = "shelter",fetch = FetchType.EAGER)
    private Set<Animal> animals;

    public Shelter() {
    }

    public Shelter(Long id, String name, String safety, LocalDateTime dateTime,
                   ShelterLocation shelterLocation, Set<Animal> animals) {
        this.id = id;
        this.name = name;
        this.safety = safety;
        this.dateTime = dateTime;
        this.shelterLocation = shelterLocation;
        this.animals = animals;
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

    public String getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        this.safety = safety;
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
        Shelter shelter = (Shelter) o;
        return Objects.equals(id, shelter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", safety='" + safety + '\'' +
                ", dateTime=" + dateTime +
                ", shelterLocation=" + shelterLocation +
                ", animals=" + animals +
                '}';
    }
}


