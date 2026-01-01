package de.assecor.personen.config;

import de.assecor.personen.api.ImportService;
import de.assecor.personen.impl.CvsImportService;
import de.assecor.personen.impl.DbImportService;
import de.assecor.personen.repository.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportConfig {

    @Bean
    public ImportService importService(PersonRepository personRepository) {
        return new DbImportService(personRepository);
    }
}
