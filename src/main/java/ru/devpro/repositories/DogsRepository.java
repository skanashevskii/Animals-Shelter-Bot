package ru.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.devpro.model.Cat;
import ru.devpro.model.Dog;

import java.util.List;


public interface DogsRepository extends JpaRepository<Dog,Long> {
    List<Dog> findByBreedIgnoreCase(String breed);
    }

