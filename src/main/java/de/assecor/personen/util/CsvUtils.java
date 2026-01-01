package de.assecor.personen.util;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import de.assecor.personen.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvUtils {

    private final static Logger logger = LoggerFactory.getLogger(CsvUtils.class);

    public static Map<Integer, Person> read(InputStream stream) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("name")
                .addColumn("firstName")
                .addColumn("adress")
                .addColumn("color")
                .setColumnSeparator(',')
                .build()
                .withoutHeader();
        ObjectReader reader = mapper.readerFor(Person.class).with(schema);

        List<Person> persons = reader.<Person>readValues(stream).readAll();
        IntStream.range(0, persons.size()).forEach(i -> persons.get(i).setId(i + 1));
        return validateMap(persons.stream().collect(Collectors.toMap(Person::getId, p -> p)));
    }

    private static Map<Integer, Person> validateMap(Map<Integer, Person> persons) {
        Map<Integer, Person> validPersons = new HashMap<>();

        for (Map.Entry<Integer, Person> entry : persons.entrySet()) {
            if(entry.getValue().getPlz() == null || entry.getValue().getPlz().isEmpty() || entry.getValue().getCity() == null || entry.getValue().getCity().isEmpty()) {
                logger.warn("Person mit der ID {} hat keine gültige Adresse. Eintrag wird ignoriert", entry.getKey());
                continue;
            }
            if((entry.getValue().getFirstName() == null || entry.getValue().getFirstName().isEmpty()) || (entry.getValue().getName() == null || entry.getValue().getName().isEmpty())) {
                logger.warn("Person mit der ID {} hat keinen vollständigen Namen. Eintrag wird ignoriert", entry.getKey());
                continue;
            }
            if(entry.getValue() == null) {
                logger.warn("Person mit der ID {} hat keinen gültigen Farbcode. Eintrag wird ignoriert", entry.getKey());
                continue;
            }
            validPersons.put(entry.getKey(), entry.getValue());
        }

        return validPersons;
    }
}