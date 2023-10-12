package ru.devpro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.model.Dog;
import ru.devpro.repositories.DogsRepository;

import java.util.Collection;

@Service
public class DogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DogService.class);

    private final DogsRepository dogsRepository;

    public DogService(DogsRepository dogsRepository) {
        this.dogsRepository = dogsRepository;
    }

    public Dog findDogById(Long dogId) {
        LOGGER.debug("Was invoked method for search dog by id: {}", dogId);
        return dogsRepository.findById(dogId).orElse(null);
    }

    public Dog createDog(Dog dog) {
        LOGGER.info("Was invoked method for create dog: {}", dog);
        String name = dog.getName();
        String breed = dog.getBreed();
        if (name.isEmpty() | name.isBlank() | breed.isEmpty() | breed.isBlank()) {
            return null;
        }
        return dogsRepository.save(dog);
    }

    public Dog editDog(Dog dog) {
        LOGGER.info("Was invoked method for edit dog : {}", dog);
        if (findDogById(dog.getId()) == null) {
            return new Dog();
        }
        return createDog(dog);
    }

    public boolean deleteDog(long dogId) {
        LOGGER.info("Was invoked method for delete dog by id: {}", dogId);
        if (findDogById(dogId) == null) {
            return false;
        }
        dogsRepository.deleteById(dogId);
        return true;
    }

    public Collection<Dog> findAllByBreedIgnoreCase(String breed) {
        LOGGER.debug("Was invoked method for search all dogs by breed: {}", breed);
        Collection<Dog> dogs = dogsRepository.findByBreedIgnoreCase(breed);
        if (dogs.isEmpty()) {
            return null;
        }
        return dogs;
    }

    public Collection<Dog> findDogsOrderedById() {
        LOGGER.debug("Was invoked method for search all dogs");
        return dogsRepository.findByOrderById();
    }

    public Collection<Dog> findDogsGroupedByBreeds() {
        LOGGER.debug("Was invoked method for search all dogs ordered by breeds");
        return dogsRepository.findByOrderByBreed();
    }

    public Collection<Dog> findByNameIgnoreCase(String name) {
        LOGGER.debug("Was invoked method for search all dogs with name: {}", name);
        Collection<Dog> dogs = dogsRepository.findAllByNameIgnoreCase(name);
        if (dogs.isEmpty()) {
            return null;
        }
        return dogs;
    }
}
