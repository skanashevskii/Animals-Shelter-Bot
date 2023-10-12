package ru.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.devpro.model.Dog;

import java.util.Collection;
import java.util.List;


public interface DogsRepository extends JpaRepository<Dog, Long> {
    List<Dog> findByBreedIgnoreCase(String breed);

    Collection<Dog> findByOrderById();

    Collection<Dog> findByOrderByBreed();

    Collection<Dog> findAllByNameIgnoreCase(String name);
}

