package ru.devpro.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.devpro.model.AvatarDog;
import ru.devpro.model.Dog;

import java.util.Optional;

@Repository
public interface AvatarDogsRepository extends JpaRepository<AvatarDog,Long> {

    Optional<AvatarDog> findDogById(Long dogId);

    @NotNull
    Page<AvatarDog> findAll(@NotNull Pageable pageable);
}
