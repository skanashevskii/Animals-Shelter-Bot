package ru.devpro.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devpro.model.AvatarCat;

import java.util.Optional;

@Repository
public interface AvatarCatsRepository extends JpaRepository<AvatarCat,Long> {

    Optional<AvatarCat> findCatById(Long studentId);

    @NotNull
    Page<AvatarCat> findAll(Pageable pageable);
}