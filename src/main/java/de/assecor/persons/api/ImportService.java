package de.assecor.persons.api;

import de.assecor.persons.model.PersonDto;

import java.util.Collection;

public interface ImportService {
    Collection<PersonDto> getPersons();
    PersonDto getPersonById(int id);
    PersonDto addPerson(PersonDto person);
    void deletePerson(int id);
    void editPerson(PersonDto person);
    Collection<PersonDto> getPersonsByColor(String color);
}