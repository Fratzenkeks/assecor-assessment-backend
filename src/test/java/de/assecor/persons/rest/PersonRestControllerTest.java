package de.assecor.persons.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.assecor.persons.api.ImportService;
import de.assecor.persons.exception.GlobalExceptionHandler;
import de.assecor.persons.exception.PersonNotFoundException;
import de.assecor.persons.exception.UnknownColorException;
import de.assecor.persons.model.Color;
import de.assecor.persons.model.PersonDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonRestController.class)
@Import({
        GlobalExceptionHandler.class,
        PersonRestControllerTest.MockConfig.class
})
class PersonRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ImportService importService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {
        @Bean
        ImportService importService() {
            return Mockito.mock(ImportService.class);
        }
    }

    @Test
    void getAllPersons_ok() throws Exception {
        when(importService.getPersons()).thenReturn(List.of(person()));

        mockMvc.perform(get("/persons").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Hans"));
    }

    @Test
    void getAllPersons_emptyList() throws Exception {
        when(importService.getPersons()).thenReturn(List.of());

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getPersonById_ok() throws Exception {
        when(importService.getPersonById(1)).thenReturn(person());

        mockMvc.perform(get("/persons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Müller"));
    }

    @Test
    void getPersonById_notFound() throws Exception {
        when(importService.getPersonById(99)).thenThrow(new PersonNotFoundException(String.format("Person mit ID %s wurde nicht gefunden", 99)));
        mockMvc.perform(get("/persons/99")).andExpect(status().isNotFound());
    }

    @Test
    void getPersonById_invalidIdFormat() throws Exception {
        mockMvc.perform(get("/persons/abc")).andExpect(status().isInternalServerError());
    }

    @Test
    void getPersonsByColor_ok() throws Exception {
        when(importService.getPersonsByColor("blau")).thenReturn(List.of(person()));

        mockMvc.perform(get("/persons/color/blau"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value("blau"));
    }

    @Test
    void getPersonsByColor_unknownColor() throws Exception {
        when(importService.getPersonsByColor("pink")).thenThrow(new UnknownColorException("pink"));
        mockMvc.perform(get("/persons/color/pink")).andExpect(status().isNotFound());
    }

    @Test
    void getPersonsByColor_caseSensitive() throws Exception {
        when(importService.getPersonsByColor("Blau")).thenThrow(new UnknownColorException("Blau"));
        mockMvc.perform(get("/persons/color/Blau")).andExpect(status().isNotFound());
    }

    @Test
    void createPerson_ok() throws Exception {
        PersonDto input = person();

        when(importService.addPerson(any(PersonDto.class))).thenReturn(input);

        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());

        verify(importService).addPerson(any(PersonDto.class));
    }

    @Test
    void deletePerson_ok() throws Exception {
        doNothing().when(importService).deletePerson(1);
        mockMvc.perform(delete("/persons/1")).andExpect(status().isOk());
    }

    @Test
    void deletePerson_notFound() throws Exception {
        doThrow(new PersonNotFoundException(String.format("Person mit ID %s wurde nicht gefunden", 1))).when(importService).deletePerson(1);
        mockMvc.perform(delete("/persons/1")).andExpect(status().isNotFound());
    }

    private static PersonDto person() {
        PersonDto dto = new PersonDto();
        dto.setId(1);
        dto.setFirstName("Hans");
        dto.setName("Müller");
        dto.setPlz("67742");
        dto.setCity("Lauterecken");
        dto.setColor(Color.BLUE);
        return dto;
    }
}