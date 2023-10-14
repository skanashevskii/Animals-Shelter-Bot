package ru.devpro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.enums.AnimalType;
import ru.devpro.model.Animal;
import ru.devpro.repositories.AnimalsRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.EnumSet;

@Service
public class AnimalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalService.class);

    private final AnimalsRepository animalsRepository;

    public AnimalService(AnimalsRepository animalsRepository) {
        this.animalsRepository = animalsRepository;
    }

    public Animal findAnimalById(Long animalId) {
        LOGGER.debug("Was invoked method for seach animal by id: {}", animalId);
        return animalsRepository.findById(animalId).orElse(null);
    }

    public Animal createAnimal(Animal animal, AnimalType animalType) {
        boolean isValidType = false;
        // Перебираем значения enum AnimalType
        for (AnimalType type : AnimalType.values()) {
            if (type == animalType) {
                isValidType = true;
                break;
            }
        }
        if(isValidType){
            LOGGER.info("Animal type {} is valid.", animalType);
            animal.setTypeAnimal(animalType);
            LocalDateTime truncatedDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
            animal.setDateTime(truncatedDateTime);
            return animalsRepository.save(animal);
        }else {
            LOGGER.warn("Invalid animal type: {}", animalType);
            throw new IllegalArgumentException("Invalid animal type: " + animalType);
        }

    }

    public Animal editAnimal(Animal animal) {
        LOGGER.info("Was invoked method for edit animal : {}", animal);
        return animalsRepository.findById(animal.getId())
                .map(dbEntity -> {
                    dbEntity.setName(animal.getName());
                    dbEntity.setBreed(animal.getBreed());
                    dbEntity.setTypeAnimal(animal.getTypeAnimal());
                    dbEntity.setText(animal.getText());
                    animalsRepository.save(dbEntity);
                    return dbEntity;
                })
                .orElse(null);
    }


    public void deleteAnimal(long animalId) {
        LOGGER.info("Was invoked method for delete animal by id: {}", animalId);
        animalsRepository.findById(animalId)
                .map(entity -> {
                    animalsRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    public Collection<Animal> findAllbyBreedIgnoreCase(String breed) {
        return animalsRepository.findByBreedIgnoreCase(breed);
    }
}
