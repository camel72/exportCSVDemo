package be.kc.persondata.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonData {

    public PersonData() {
    }

    public PersonData(String lastName, String firstName, LocalDate birthDate, LocalDate dateOfDeath, String street, String number, String city, String ssin, String affiliation) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        DateOfDeath = dateOfDeath;
        this.street = street;
        this.number = number;
        this.city = city;
        this.ssin = ssin;
        this.affiliation = affiliation;
    }

    @CsvBindByPosition(position = 0)
    private String lastName;
    @CsvBindByPosition(position = 1)
    private String firstName;
    @CsvDate(value = "dd/MM/yy")
    @CsvBindByPosition(position = 2)
    private LocalDate birthDate;
    @CsvDate(value = "dd/MM/yy")
    @CsvBindByPosition(position = 3)
    private LocalDate DateOfDeath;
    @CsvBindByPosition(position = 4)
    private String street;
    @CsvBindByPosition(position = 5)
    private String number;
    @CsvBindByPosition(position = 6)
    private String city;
    @CsvBindByPosition(position = 7)
    private String ssin;
    @CsvBindByPosition(position = 8)
    private String affiliation;
}