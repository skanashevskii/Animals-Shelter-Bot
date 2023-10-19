package ru.devpro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.dto.AnimalDTO;

import ru.devpro.enums.AnimalType;
import ru.devpro.mapers.AnimalMapper;

import ru.devpro.model.Animal;

import ru.devpro.repositories.AnimalsRepository;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Optional;


@Service
public class AnimalServiceImpl implements AnimalService{
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalServiceImpl.class);

    private final AnimalsRepository animalsRepository;
    private final AnimalMapper animalMapper;

    public AnimalServiceImpl(AnimalsRepository animalsRepository) {
        this.animalsRepository = animalsRepository;
        this.animalMapper = AnimalMapper.INSTANCE;
    }
    @Override
    public AnimalDTO findAnimalById(Long animalId) {
        LOGGER.debug("Was invoked method for search animal by id: {}", animalId);

        Optional<Animal> animalOptional = animalsRepository.findById(animalId);

        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();
            // маппер для преобразования сущности Animal в DTO
            return animalMapper.toDTO(animal);
        } else {
            return null;
        }
    }

    @Override
    public AnimalDTO createAnimal(AnimalDTO animalDTO, AnimalType animalType) {
        LOGGER.info("Received request to save shelter: {}", animalDTO);

        if (!isValidAnimalType(animalType)) {
            LOGGER.warn("Invalid animal type: {}", animalType);
            throw new IllegalArgumentException("Invalid animal type: " + animalType);
        }

        // Преобразуйте DTO в сущность
        Animal animalEntity = animalMapper.toEntity(animalDTO);
        animalEntity.setType_animal(animalType);

        // Установите дату и время
        LocalDateTime truncatedDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        animalEntity.setDateTime(truncatedDateTime);

        // Сохраните сущность в репозитории
        Animal savedEntity = animalsRepository.save(animalEntity);

        // Преобразуйте сущность обратно в DTO
        return animalMapper.toDTO(savedEntity);
    }

    public AnimalDTO editAnimal(Long id, AnimalDTO animalDTO, AnimalType type) {
        // Проверка валидности типа животного
        if (!isValidAnimalType(type)) {
            LOGGER.warn("Invalid animal type: {}", type);
            throw new IllegalArgumentException("Invalid animal type: " + type);
        }

        // Поиск животного в репозитории по ID
        return animalsRepository.findById(id)
                .map(dbEntity -> {
                    // Обновление полей сущности на основе данных из DTO
                    dbEntity.setName(animalDTO.getName());
                    dbEntity.setBreed(animalDTO.getBreed());
                    dbEntity.setType_animal(type); // Обновление типа животного
                    dbEntity.setText(animalDTO.getText());

                    // Обновление времени изменения записи
                    LocalDateTime now = LocalDateTime.now();
                    dbEntity.setDateTime(now.truncatedTo(ChronoUnit.SECONDS));

                    // Сохранение обновленной сущности в репозитории
                    animalsRepository.save(dbEntity);

                    // Преобразование сущности в DTO для возврата
                    return animalMapper.toDTO(dbEntity);
                })
                .orElse(null);
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

    //валидность типа животного
    private boolean isValidAnimalType(AnimalType animalType) {
        for (AnimalType type : AnimalType.values()) {
            if (type == animalType) {
                return true;
            }
        }
        return false;
    }
}
