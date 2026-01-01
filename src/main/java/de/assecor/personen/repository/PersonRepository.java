package de.assecor.personen.repository;

import de.assecor.personen.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    List<PersonEntity> findByColor(String color);
}
