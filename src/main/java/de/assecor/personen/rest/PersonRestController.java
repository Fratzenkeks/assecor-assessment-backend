package de.assecor.personen.rest;

import de.assecor.personen.api.ImportService;
import de.assecor.personen.model.PersonDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/persons")
public class PersonRestController {

    private ImportService  importService;

    public PersonRestController(ImportService importService) {
        this.importService = importService;
    }

    @Operation(summary = "Gibt alle im System vorhandenen Personen zurück")
    @GetMapping
    public Collection<PersonDto> getPersons() {
        return importService.getPersons();
    }

    @Operation(summary = "Liefert die Person, die zur übergebenen ID passt.")
    @GetMapping("/{id:\\d+}")
    public PersonDto getPerson(@PathVariable int id) {
        return importService.getPersonById(id);
    }

    @Operation(summary = "Legt eine neue Person an und gibt diese mit der neuen ID zurück")
    @PostMapping
    public PersonDto addPerson(@RequestBody PersonDto person) {
        return importService.addPerson(person);
    }

    @Operation(summary = "Bearbeitet die Person zur übergebenen ID")
    @PutMapping("/{id:\\d+}")
    public void updatePerson(@PathVariable int id,  @RequestBody PersonDto person) {
        person.setId(id);
        importService.editPerson(person);
    }

    @Operation(summary = "Löscht die Person zur übergebenen ID")
    @DeleteMapping("/{id:\\d+}")
    public void deletePerson(@PathVariable int id) {
        importService.deletePerson(id);
    }

    @Operation(summary = "Gibt alle Personen zurück, die die übergebene Farbe haben. Farbe muss als Wort übergeben werden.")
    @GetMapping("/color/{color}")
    public Collection<PersonDto> getPersonsByColor(@PathVariable String color) {
        return importService.getPersonsByColor(color);
    }
}