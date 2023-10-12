package ru.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.devpro.model.Dog;

import java.util.Collection;

public interface DogsRepository extends JpaRepository<Dog, Long> {
    Collection<Dog> findByBreedIgnoreCase(String breed);

    Collection<Dog> findByOrderById();

    Collection<Dog> findByOrderByBreed();

    Collection<Dog> findAllByNameIgnoreCase(String name);
}