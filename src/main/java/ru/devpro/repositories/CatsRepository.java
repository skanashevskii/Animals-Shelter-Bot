package ru.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.devpro.model.Cat;

import java.util.Collection;

public interface CatsRepository extends JpaRepository<Cat, Long> {
    Collection<Cat> findByBreedIgnoreCase(String breed);

    Collection<Cat> findByOrderById();

    Collection<Cat> findByOrderByBreed();

    Collection<Cat> findAllByNameIgnoreCase(String name);
}
