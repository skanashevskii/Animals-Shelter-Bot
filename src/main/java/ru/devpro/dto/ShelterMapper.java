package ru.devpro.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.devpro.model.Shelter;
import ru.devpro.model.ShelterLocation;

@Mapper
public interface ShelterMapper {
        ShelterMapper INSTANCE = Mappers.getMapper(ShelterMapper.class);

        // Маппинг для Shelter
        @Mapping(source = "id",target = "id")
        @Mapping(source = "name",target = "name")

        ShelterDTO shelterEntityToShelterDTO(Shelter shelter);
        @Mapping(source = "id",target = "id")
        @Mapping(source = "name",target = "name")

        Shelter shelterDTOToShelterEntity(ShelterDTO shelterDTO);

        // Маппинг для ShelterLocation
        @Mapping(source = "id",target = "id")
        @Mapping(source = "address",target = "address")
        @Mapping(source = "city",target = "city")
        @Mapping(source = "state",target = "state")
        @Mapping(source = "zipcode",target = "zipcode")

        ShelterLocationDTO shelterLocationEntityToShelterLocationDTO(ShelterLocation shelterLocation);
        @Mapping(source = "id",target = "id")
        @Mapping(source = "address",target = "address")
        @Mapping(source = "city",target = "city")
        @Mapping(source = "state",target = "state")
        @Mapping(source = "zipcode",target = "zipcode")
        ShelterLocation shelterLocationDTOToShelterLocationEntity(ShelterLocationDTO shelterLocationDTO);
    }

