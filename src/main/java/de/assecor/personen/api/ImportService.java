package de.assecor.personen.api;

import de.assecor.personen.model.PersonDto;

import java.util.Collection;

public interface ImportService {
    Collection<PersonDto> getPersons();
    PersonDto getPersonById(int id);
    PersonDto addPerson(PersonDto person);
    void deletePerson(int id);
    void editPerson(PersonDto person);
    Collection<PersonDto> getPersonsByColor(String color);
}