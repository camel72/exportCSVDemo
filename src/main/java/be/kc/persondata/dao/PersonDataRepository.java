package be.kc.persondata.dao;

import be.kc.persondata.model.PersonData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDataRepository extends JpaRepository<PersonData, String> {
}
