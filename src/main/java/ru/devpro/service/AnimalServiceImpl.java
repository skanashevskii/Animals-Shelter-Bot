package ru.devpro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.dto.AnimalDTO;

import ru.devpro.mapers.AnimalMapper;

import ru.devpro.model.Animal;

import ru.devpro.repositories.AnimalsRepository;


import java.util.Collection;


@Service
public class AnimalServiceImpl implements AnimalService{
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalServiceImpl.class);

    private final AnimalsRepository animalsRepository;
    private final AnimalMapper animalMapper;

    public AnimalServiceImpl(AnimalsRepository animalsRepository) {
        this.animalsRepository = animalsRepository;
        this.animalMapper = AnimalMapper.INSTANCE;
    }

    public Animal findAnimalById(Long animalId) {
        LOGGER.debug("Was invoked method for seach animal by id: {}", animalId);
        return animalsRepository.findById(animalId).orElse(null);
    }

 /*   public Animal createAnimal(Animal animal, AnimalType animalType) {
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
            animal.setType_animal(animalType);
            LocalDateTime truncatedDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
            animal.setDateTime(truncatedDateTime);
            return animalsRepository.save(animal);
        }else {
            LOGGER.warn("Invalid animal type: {}", animalType);
            throw new IllegalArgumentException("Invalid animal type: " + animalType);
        }

    }*/
 @Override
 public AnimalDTO createAnimal(AnimalDTO animalDTO) {
     LOGGER.info("Received request to save shelter: {}", animalDTO);
     Animal animalEntity = animalMapper.toEntity(animalDTO); // Преобразуйте DTO в сущность
     Animal savedEntity = animalsRepository.save(animalEntity);
     return animalMapper.toDTO(savedEntity); // Преобразуйте сущность обратно в DTO
 }

    public Animal editAnimal(Animal animal) {
        LOGGER.info("Was invoked method for edit animal : {}", animal);
        return animalsRepository.findById(animal.getId())
                .map(dbEntity -> {
                    dbEntity.setName(animal.getName());
                    dbEntity.setBreed(animal.getBreed());
                    dbEntity.setType_animal(animal.getType_animal());
                    dbEntity.setText(animal.getText());
                    animalsRepository.save(dbEntity);
                    return dbEntity;
                })
                .orElse(null);
    }

    @Override
    public Animal findUserById(Long animalId) {
        return null;
    }

    @Override
    public Collection<Animal> findAll() {
        return null;
    }


    public void deleteAnimal(Long animalId) {
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
