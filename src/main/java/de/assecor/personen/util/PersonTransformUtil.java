package de.assecor.personen.util;

import de.assecor.personen.model.Color;
import de.assecor.personen.model.Person;
import de.assecor.personen.model.PersonDto;
import de.assecor.personen.model.PersonEntity;

public class PersonTransformUtil {

    public static PersonDto csvToDto(Person person) {
        PersonDto dto = new PersonDto();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setName(person.getName());
        dto.setPlz(person.getPlz());
        dto.setCity(person.getCity());
        dto.setColor(Color.getColorFromCode(person.getColor()));
        return dto;
    }

    public static Person dtoToCsv(PersonDto person) {
        Person p = new Person();
        p.setId(person.getId());
        p.setFirstName(person.getFirstName());
        p.setName(person.getName());
        p.setPlz(person.getPlz());
        p.setCity(person.getCity());
        p.setColor(person.getColor().getCode());
        return p;
    }

    public static PersonDto entityToDto(PersonEntity person) {
        PersonDto dto = new PersonDto();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setName(person.getName());
        dto.setPlz(person.getPlz());
        dto.setCity(person.getCity());
        dto.setColor(Color.getColorFromCode(Integer.parseInt(person.getColor())));
        return dto;
    }

    public static PersonEntity dtoToEntity(PersonDto person) {
        PersonEntity p = new PersonEntity();
        if(person.getId() != null) {
            p.setId(person.getId());
        }
        p.setFirstName(person.getFirstName());
        p.setName(person.getName());
        p.setPlz(person.getPlz());
        p.setCity(person.getCity());
        p.setColor(String.valueOf(person.getColor().getCode()));
        return p;
    }
}
