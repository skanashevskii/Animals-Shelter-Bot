package ru.devpro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.devpro.enums.AnimalType;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @ManyToOne
    @JoinColumn(name = "sheltor_location_id")
    private ShelterLocation shelterLocation;


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
}
