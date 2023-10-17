package ru.devpro.service;

import org.springframework.stereotype.Service;
import ru.devpro.dto.ShelterDTO;
import ru.devpro.model.Shelter;


import java.util.Collection;

@Service
public interface ShelterService {
    void deleteShelter(Long id);



    Shelter editShelter(Shelter shelter);

    Shelter findShelterById(Long userId);

    Collection<Shelter> findAll();

    ShelterDTO createShelter(ShelterDTO shelterDTO); // Метод для создания приюта

    // Другие методы
}

