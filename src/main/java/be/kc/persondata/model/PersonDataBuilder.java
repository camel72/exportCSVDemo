package be.kc.persondata.model;

import java.time.LocalDate;

public class PersonDataBuilder {
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private LocalDate dateOfDeath;
    private String street;
    private String number;
    private String city;
    private String ssin;
    private String affiliation;

    public PersonDataBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PersonDataBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PersonDataBuilder setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public PersonDataBuilder setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
        return this;
    }

    public PersonDataBuilder setStreet(String street) {
        this.street = street;
        return this;
    }

    public PersonDataBuilder setNumber(String number) {
        this.number = number;
        return this;
    }

    public PersonDataBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public PersonDataBuilder setSsin(String ssin) {
        this.ssin = ssin;
        return this;
    }

    public PersonDataBuilder setAffiliation(String affiliation) {
        this.affiliation = affiliation;
        return this;
    }

    public PersonData createPersonData() {
        return new PersonData(lastName, firstName, birthDate, dateOfDeath, street, number, city, ssin, affiliation);
    }
}