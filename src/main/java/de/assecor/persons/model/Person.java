package de.assecor.persons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Person {

    private int  id;
    private String firstName;
    private String name;
    private String plz;
    private String city;
    private int color;

    public Person() {}

    @JsonSetter("adress")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setAdress(String adress) {
        String[] parts = adress.trim().split(" ", 2);
        if (parts.length == 2) {
            this.plz = parts[0];
            this.city = parts[1];
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
