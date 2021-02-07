package be.kc.persondata.controller.v1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDataDTO {
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private LocalDate DateOfDeath;
    private String street;
    private Integer number;
    private String city;
    private String ssin;
    private String affiliation;
}
