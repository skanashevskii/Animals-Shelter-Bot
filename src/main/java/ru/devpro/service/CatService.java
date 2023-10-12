package ru.devpro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.model.Cat;
import ru.devpro.repositories.CatsRepository;

import java.util.Collection;

@Service
public class CatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatService.class);

    private final CatsRepository catsRepository;

    public CatService(CatsRepository catsRepository) {
        this.catsRepository = catsRepository;
    }

    public Cat findCatById(Long catId) {
        LOGGER.debug("Was invoked method for search cat by id: {}", catId);
        return catsRepository.findById(catId).orElse(null);
    }

    public Cat createCat(Cat cat) {
        LOGGER.info("Was invoked method for create cat: {}", cat);
        String name = cat.getName();
        String breed = cat.getBreed();
        if (name.isEmpty() | name.isBlank() | breed.isEmpty() | breed.isBlank()) {
            return null;
        }
        return catsRepository.save(cat);
    }

    public Cat editCat(Cat cat) {
        LOGGER.info("Was invoked method for edit cat : {}", cat);
        if (findCatById(cat.getId()) == null) {
            return new Cat();
        }
        return createCat(cat);
    }

    public boolean deleteCat(long catId) {
        LOGGER.info("Was invoked method for delete cat by id: {}", catId);
        if (findCatById(catId) == null) {
            return false;
        }
        catsRepository.deleteById(catId);
        return true;
    }

    public Collection<Cat> findAllByBreedIgnoreCase(String breed) {
        LOGGER.debug("Was invoked method for search all cats by breed: {}", breed);
        Collection<Cat> cats = catsRepository.findByBreedIgnoreCase(breed);
        if (cats.isEmpty()) {
            return null;
        }
        return cats;
    }

    public Collection<Cat> findCatsOrderedById() {
        LOGGER.debug("Was invoked method for search all cats");
        return catsRepository.findByOrderById();
    }

    public Collection<Cat> findCatsGroupedByBreeds() {
        LOGGER.debug("Was invoked method for search all cats ordered by breeds");
        return catsRepository.findByOrderByBreed();
    }

    public Collection<Cat> findByNameIgnoreCase(String name) {
        LOGGER.debug("Was invoked method for search all cats with name: {}", name);
        Collection<Cat> cats = catsRepository.findAllByNameIgnoreCase(name);
        if (cats.isEmpty()) {
            return null;
        }
        return cats;
    }
}