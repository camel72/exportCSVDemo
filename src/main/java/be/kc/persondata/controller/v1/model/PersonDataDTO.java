package be.kc.persondata.controller.v1.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonDataDTO {

    private Long id;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private LocalDate DateOfDeath;
    private String street;
    private String number;
    private String city;
    private String ssin;
    private String affiliation;
}
