package ru.devpro.service;


import ru.devpro.dto.ShelterLocationDTO;

import ru.devpro.model.ShelterLocation;

import java.util.Collection;

public interface ShelterLocationService {
    void deleteShelterLocation(Long id);

    ShelterLocation editShelterLocation(ShelterLocation shelterLocation);

   ShelterLocation findShelterById(Long userId);

    Collection<ShelterLocation> findAll();

    ShelterLocationDTO createShelterLocation(ShelterLocationDTO shelterLocationDTO); // Метод для создания адреса приюта

    // Другие методы
}
