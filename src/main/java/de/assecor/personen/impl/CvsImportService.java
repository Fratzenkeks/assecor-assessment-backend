package de.assecor.personen.impl;

import de.assecor.personen.api.ImportService;
import de.assecor.personen.exceptions.BrokenResourceException;
import de.assecor.personen.exceptions.PersonNotFoundException;
import de.assecor.personen.exceptions.ResourceNotFoundException;
import de.assecor.personen.exceptions.UnknownColorException;
import de.assecor.personen.model.Color;
import de.assecor.personen.model.Person;
import de.assecor.personen.model.PersonDto;
import de.assecor.personen.util.CsvUtils;
import de.assecor.personen.util.PersonTransformUtil;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CvsImportService  implements ImportService {

    private Map<Integer, Person> persons = new HashMap<>();

    @PostConstruct
    public void init() {
        try {
            persons = CsvUtils.read(getClass().getClassLoader().getResourceAsStream("sample-input.csv"));
        } catch (IOException e) {
            throw new BrokenResourceException("Personen konnten nicht aus der CSV-Datei gelesen werden: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("CSV-Datei für den Import wurde nicht gefunden: " + e.getMessage());
        }
    }

    @Override
    public Collection<PersonDto> getPersons() {
        if(persons.isEmpty()) {
            throw new PersonNotFoundException("Keine Personen vorhanden");
        }
        return persons.values().stream().map(PersonTransformUtil::csvToDto).collect(Collectors.toList());
    }

    @Override
    public PersonDto getPersonById(int id) {
        if(persons.get(id) == null) {
            throw new PersonNotFoundException(String.format("Person mit ID %s wurde nicht gefunden", id));
        }
        return PersonTransformUtil.csvToDto(persons.get(id));
    }

    @Override
    public PersonDto addPerson(PersonDto person) {
        person.setId(persons.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1);
        Person p = PersonTransformUtil.dtoToCsv(person);
        persons.put(p.getId(), p);
        return PersonTransformUtil.csvToDto(p);
    }

    @Override
    public void deletePerson(int id) {
        if(persons.get(id) == null) {
            throw new PersonNotFoundException(String.format("Person mit ID %s wurde nicht gefunden", id));
        }
        persons.remove(id);
    }

    @Override
    public void editPerson(PersonDto person) {
        if(person.getId() == 0 || persons.get(person.getId()) == null) {
            throw new PersonNotFoundException(String.format("Person mit ID %s wurde nicht gefunden", person.getId()));
        }
        persons.put(person.getId(), PersonTransformUtil.dtoToCsv(person));
    }

    @Override
    public Collection<PersonDto> getPersonsByColor(String color) {
        if(color == null || color.isEmpty() || Color.getColorFromText(color) == null) {
            throw new UnknownColorException("Übergebene Farbe wurde nicht gefunden");
        }
        return persons.values().stream()
                               .filter(p -> p.getColor() == Color.getColorFromText(color).getCode())
                               .map(PersonTransformUtil::csvToDto)
                               .collect(Collectors.toList());
    }
}