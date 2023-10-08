package ru.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.devpro.model.Cat;

import java.util.List;

public interface CatsRepository extends JpaRepository<Cat,Long> {
    List<Cat> findByBreedIgnoreCase(String breed);
}
