package be.kc.persondata.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class PersonData {



    public PersonData() {
    }

    public PersonData(String lastName, String firstName, LocalDate birthDate, LocalDate dateOfDeath, String street, String number, String box, String city, String ssin, String affiliation) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        DateOfDeath = dateOfDeath;
        this.street = street;
        this.number = number;
        this.box = box;
        this.city = city;
        this.ssin = ssin;
        this.affiliation = affiliation;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    @CsvBindByPosition(position = 0)
    private String lastName;

    @Column
    @CsvBindByPosition(position = 1)
    private String firstName;

    @Column
    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByPosition(position = 2)
    private LocalDate birthDate;

    @Column
    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByPosition(position = 3)
    private LocalDate DateOfDeath;

    @Column
    @CsvBindByPosition(position = 4)
    private String street;

    @Column
    @CsvBindByPosition(position = 5)
    private String number;

    @Column
    @CsvBindByPosition(position = 6)
    private String box;

    @Column
    @CsvBindByPosition(position = 7)
    private String city;

    @Column
    @CsvBindByPosition(position = 8)
    private String ssin;

    @Column
    @CsvBindByPosition(position = 9)
    private String affiliation;
}