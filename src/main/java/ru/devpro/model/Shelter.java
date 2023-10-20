package ru.devpro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "shelter")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

   /* @OneToMany(mappedBy = "shelter",fetch = FetchType.EAGER)
    private Set<Animal> animals;*/
    @OneToMany(mappedBy = "shelter")
    private List<ShelterLocation> shelterLocations;


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
}


