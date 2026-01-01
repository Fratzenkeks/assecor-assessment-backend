package de.assecor.persons.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import de.assecor.persons.exception.UnknownColorException;
import de.assecor.persons.model.Color;

import java.io.IOException;

public class ColorDeserializer extends JsonDeserializer<Color> {
    @Override
    public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String color = p.getText().trim();
        try {
            int code = Integer.parseInt(color);
            return Color.getColorFromCode(code);
        } catch (NumberFormatException e) {
            try {
                return Color.getColorFromText(color);
            } catch (IllegalArgumentException ex) {
                throw new UnknownColorException("Ungültiger Farbwert: " + color);
            }
        }
    }
}