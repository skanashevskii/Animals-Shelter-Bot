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
        LOGGER.debug("Was invoked method for seach student by id: {}", catId);
        return catsRepository.findById(catId).orElse(null);
    }

    public Collection<Cat> getAllCats() {
        LOGGER.info("Getting all cats ...");
        return catsRepository.findAll();
    }


    public Collection<Cat> getCatsByGroupId(long catId) {
        return null;
    }


    public Cat createCat(Cat cat) {
        LOGGER.info("Was invoked method for create student: {}", cat);
        return catsRepository.save(cat);
    }

    public Cat editCat(Cat cat) {
        LOGGER.info("Was invoked method for edit student : {}", cat);
        return catsRepository.findById(cat.getId())
                .map(dbEntity -> {
                    dbEntity.setName(cat.getName());
                    dbEntity.setBreed(cat.getBreed());
                    catsRepository.save(dbEntity);
                    return dbEntity;
                })
                .orElse(null);
    }


    public void deleteCat(long catId) {
        LOGGER.info("Was invoked method for delete student by id: {}", catId);
        catsRepository.findById(catId)
                .map(entity -> {
                    catsRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    public Collection<Cat> findAllbyBreedIgnoreCase(String color) {
        return null;
    }
}
