package be.kc.persondata.dao;

import be.kc.persondata.model.PersonData;
import be.kc.persondata.model.PersonDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.util.Assert;

import java.time.LocalDate;

@DataJpaTest
class PersonDataRepositoryIntegrationTest {

    @Autowired
    private PersonDataRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByLastName() {
        PersonData personData = new PersonDataBuilder().setLastName("testLastName").createPersonData();
        entityManager.persist(personData);
        Assert.notEmpty(repository.findByLastName("testLastName"), "personData list is empty");
    }

    @Test
    public void testFindByLastNameAndFirstName() {
        PersonData personData = new PersonDataBuilder().setLastName("testLastName").setFirstName("testFirstName").createPersonData();
        entityManager.persist(personData);
        Assert.notEmpty(repository.findByLastNameAndFirstName("testLastName", "testFirstName"),
                "personData list is empty");
    }

    @Test
    public void testFindByLastNameAndFirstNameAndBirthDate(){
        PersonData personData = new PersonDataBuilder().setLastName("testLastName").setFirstName("testFirstName").setBirthDate(LocalDate.of(2000, 1, 1)).createPersonData();
        entityManager.persist(personData);
        Assert.notEmpty(repository.findByLastNameAndFirstNameAndBirthDate("testLastName", "testFirstName",
                LocalDate.of(2000, 1, 1)), "list is empty");
    }
}