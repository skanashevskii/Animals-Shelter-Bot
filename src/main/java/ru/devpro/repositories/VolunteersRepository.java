package ru.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.devpro.model.Dog;
import ru.devpro.model.Volunteer;

public interface VolunteersRepository extends JpaRepository<Volunteer,Long> {
}
