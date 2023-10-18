package ru.devpro.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import java.util.Objects;


@Entity
@Table(name = "shelter_location")
public class ShelterLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Адрес приюта")
    @NotNull
    @Column(name = "address")
    private String address;

    @Schema(description = "Город")
    @NotNull
    @Column(name = "city")
    private String city;

    @Schema(description = "Район")
    @NotNull
    @Column(name = "state")
    private String state;
    @Schema(description = "Почтовый индекс")
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name="shelter_location_time", nullable = false)
    private LocalDateTime dateTime;

    @OneToOne
    private Shelter shelter;

    public ShelterLocation() {
    }

    public ShelterLocation(Long id, String address, String city, String state,
                           String zipcode, LocalDateTime dateTime, Shelter shelter) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.dateTime = dateTime;
        this.shelter = shelter;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShelterLocation that = (ShelterLocation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ShelterLocation{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", dateTime=" + dateTime +
                ", shelter=" + shelter +
                '}';
    }
}
