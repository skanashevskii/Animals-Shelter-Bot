package ru.devpro.service;

import org.springframework.stereotype.Service;
import ru.devpro.dto.AnimalDTO;

import ru.devpro.enums.AnimalType;
import ru.devpro.model.Animal;


import java.util.Collection;
@Service

public interface AnimalService {
    AnimalDTO createAnimal(AnimalDTO animalDTO, AnimalType animalType); // Метод для создания животного

    AnimalDTO editAnimal(Long id,AnimalDTO animal,AnimalType animalType);

    void deleteAnimal(Long animalId);

    AnimalDTO findAnimalById(Long animalId);

    Collection<AnimalDTO> findAll();


    Collection<AnimalDTO> findAllByBreedIgnoreCase(String breed);
}
