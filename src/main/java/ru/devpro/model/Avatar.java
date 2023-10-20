package ru.devpro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "avatar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "путь к файлу на диске(оригиналу)")
    private String filePath;
    @Schema(description = "размер файла")
    private long fileSize;
    @Schema(description = "Тип файла")
    private String mediaType;
    @Lob
    @Schema(description = "массив байт(хранение в БД)")
    private byte[] preview;
    @OneToOne(fetch = FetchType.EAGER)
    private Animal animal;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return Objects.equals(id, avatar.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
