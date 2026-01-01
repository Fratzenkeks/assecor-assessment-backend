package de.assecor.persons.config;

import de.assecor.persons.api.ImportService;
import de.assecor.persons.service.DbImportService;
import de.assecor.persons.repository.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportConfig {

    @Bean
    public ImportService importService(PersonRepository personRepository) {
        return new DbImportService(personRepository);
    }
}
