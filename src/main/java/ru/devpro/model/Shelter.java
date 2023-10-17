package ru.devpro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;



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

    public Shelter() {
    }

    public String getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        this.safety = safety;
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
                '}';
    }
}


