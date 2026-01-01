package de.assecor.personen.impl;

import de.assecor.personen.api.ImportService;
import de.assecor.personen.exceptions.UnknownColorException;
import de.assecor.personen.model.Color;
import de.assecor.personen.model.PersonDto;
import de.assecor.personen.model.PersonEntity;
import de.assecor.personen.repository.PersonRepository;
import de.assecor.personen.util.PersonTransformUtil;

import java.util.Collection;
import java.util.stream.Collectors;

public class DbImportService implements ImportService {

    private final PersonRepository repository;

    public DbImportService(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<PersonDto> getPersons() {
        return repository.findAll().stream().map(PersonTransformUtil::entityToDto).collect(Collectors.toList());
    }

    @Override
    public PersonDto getPersonById(int id) {
        return repository.findById((long) id).map(PersonTransformUtil::entityToDto).orElse(null);
    }

    @Override
    public PersonDto addPerson(PersonDto person) {
        PersonEntity personEntity = repository.save(PersonTransformUtil.dtoToEntity(person));
        return PersonTransformUtil.entityToDto(personEntity);
    }

    @Override
    public void deletePerson(int id) {
        repository.deleteById((long) id);
    }

    @Override
    public void editPerson(PersonDto person) {
        repository.save(PersonTransformUtil.dtoToEntity(person));
    }

    @Override
    public Collection<PersonDto> getPersonsByColor(String color) {
        if(color == null || color.isEmpty()) {
            throw new UnknownColorException("Übergebene Farbe wurde nicht gefunden");
        }
        return repository.findByColor(String.valueOf(Color.getColorFromText(color).getCode())).stream().map(PersonTransformUtil::entityToDto).collect(Collectors.toList());
    }
}
