package ru.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.devpro.model.Dog;
import ru.devpro.model.Owner;

public interface OwnersRepository extends JpaRepository<Owner,Long> {
}
