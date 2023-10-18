package ru.devpro.mapers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ru.devpro.dto.ShelterLocationDTO;

import ru.devpro.model.ShelterLocation;
@Mapper
public interface ShelterLocationMapper {
    ShelterLocationMapper INSTANCE = Mappers.getMapper(ShelterLocationMapper.class);
    ShelterLocation toEntity(ShelterLocationDTO shelterLocationDTO); // Метод для преобразования DTO в сущность
    ShelterLocationDTO toDTO(ShelterLocation shelterLocation); // Метод для преобразования сущности в DTO
}
