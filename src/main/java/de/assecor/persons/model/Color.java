package de.assecor.persons.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.assecor.persons.exception.UnknownColorException;

import java.util.Arrays;

public enum Color {
    BLUE(1, "blau"),
    GREEN(2, "grün"),
    VIOLET(3, "violett"),
    RED(4, "rot"),
    YELLOW(5, "gelb"),
    CYAN(6, "türkis"),
    WHITE(7, "weiß");

    private final int code;
    private final String name;

    Color(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static Color getColorFromText(String value) {
        return Arrays.stream(values())
                .filter(c -> c.name.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new UnknownColorException("Unbekannte Farbe: " + value));
    }

    public static Color getColorFromCode(int code) {
        return Arrays.stream(values())
                .filter(c -> c.code == code)
                .findFirst()
                .orElseThrow(() -> new UnknownColorException("Unbekannter Farbcode: " + code));
    }
}