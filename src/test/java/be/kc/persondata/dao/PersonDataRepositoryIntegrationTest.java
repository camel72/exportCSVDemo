package be.kc.persondata.dao;

import be.kc.persondata.model.PersonData;
import be.kc.persondata.model.PersonDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PersonDataRepositoryIntegrationTest {

    @Autowired
    private PersonDataRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void init() {
        PersonData personData1 = new PersonDataBuilder()
                .setLastName("testLastName")
                .setFirstName("testFirstName")
                .setBirthDate(LocalDate.of(2000, 1, 1))
                .setStreet("testStreet1")
                .setNumber("1")
                .setCity("testCity1")
                .createPersonData();

        PersonData personData2 = new PersonDataBuilder()
                .setLastName("testLastName2")
                .setFirstName("testFirstName2")
                .setBirthDate(LocalDate.of(2001, 1, 1))
                .setStreet("testStreet2")
                .setNumber("2")
                .setCity("testCity")
                .createPersonData();

        entityManager.persist(personData1);
        entityManager.persist(personData2);
    }

    @Test
    public void testFindByLastName() {
        List<PersonData> actual = repository.findByLastName("testLastName");

        Assert.notEmpty(actual, "personData list is empty");
        assertEquals(1, actual.size());
        assertEquals("testLastName", actual.get(0).getLastName());
    }

    @Test
    public void testFindByLastNameAndFirstName() {
        List<PersonData> actual = repository.findByLastNameAndFirstName("testLastName", "testFirstName");

        Assert.notEmpty(actual, "personData list is empty");
        assertEquals(1, actual.size());
        assertEquals("testLastName", actual.get(0).getLastName());
        assertEquals("testFirstName", actual.get(0).getFirstName());
    }

    @Test
    public void testFindByLastNameAndFirstNameAndBirthDate() {
        List<PersonData> actual = repository.findByLastNameAndFirstNameAndBirthDate("testLastName", "testFirstName", LocalDate.of(2000, 1, 1));

        Assert.notEmpty(actual, "personData list is empty");
        assertEquals(1, actual.size());
        assertEquals("testLastName", actual.get(0).getLastName());
        assertEquals("testFirstName", actual.get(0).getFirstName());
        assertEquals(LocalDate.of(2000, 1, 1), actual.get(0).getBirthDate());
    }

    @Test
    public void findAllOrderByLastNameAndFirstNameAndCity() {
        List<PersonData> actual = repository.findAll();
        Assert.notEmpty(actual, "personData list is empty");
        assertEquals(2, actual.size());
    }

    @Test
    public void findByStreetAndNumberAndCity(){
        List<PersonData> actual = repository.findByStreetAndNumberAndCity("testStreet1", "1", "testCity1");

        Assert.notEmpty(actual, "personData list is empty");
        assertEquals(1, actual.size());
        assertEquals("testStreet1", actual.get(0).getStreet());
        assertEquals("1", actual.get(0).getNumber());
    }
}