package be.kc.persondata.dao;

import be.kc.persondata.model.PersonData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PersonDataRepository extends JpaRepository<PersonData, String> {

    List<PersonData> findByLastName(String lastName);

    List<PersonData> findByLastNameAndFirstName(String lastName, String firstName);

    List<PersonData> findByLastNameAndFirstNameAndBirthDate(String lastName, String firstName, LocalDate birthDate);

    List<PersonData> findByStreetAndNumberAndCity(String street, String number, String city);
}
