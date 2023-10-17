package ru.devpro.service;

import org.springframework.stereotype.Service;
import ru.devpro.dto.AnimalDTO;
import ru.devpro.dto.UserDTO;
import ru.devpro.model.Animal;
import ru.devpro.model.User;

import java.util.Collection;
@Service

public interface AnimalService {
    AnimalDTO createAnimal(AnimalDTO animalDTO); // Метод для создания животного

    Animal editAnimal(Animal animal);

    void deleteAnimal(Long animalId);

    Animal findUserById(Long animalId);

    Collection<Animal> findAll();
}
