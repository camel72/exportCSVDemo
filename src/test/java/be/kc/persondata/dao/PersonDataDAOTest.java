package be.kc.persondata.dao;

import be.kc.persondata.model.PersonData;
import be.kc.persondata.model.PersonDataBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = PersonDataDAO.class)
//@ExtendWith(SpringExtension.class)
public class PersonDataDAOTest {

    @Autowired
    private PersonDataDAO personDataDAO;

    @Test
    public void getPersonDataTest() throws Exception {

        PersonData personDataExpected = new PersonDataBuilder()
                .setLastName("testLastName")
                .setFirstName("testFirstName")
                .setBirthDate(LocalDate.of(2000, 1, 1))
                .setStreet("testAddress")
                .setNumber("1")
                .setCity("testCity")
                .setAffiliation("180")
                .createPersonData();

        List<PersonData> personDataList = personDataDAO.getPersonData("testLastName");
        assertNotNull(personDataList);
        assertNotNull(personDataList.get(0));
        assertEquals(personDataExpected, personDataList.get(0));
    }

    @Test
    public void csvTest() throws IOException, CsvException, URISyntaxException {
        Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource("export.csv").toURI()));

        CsvToBean<PersonData> csvToBean = new CsvToBeanBuilder(reader)
                .withType(PersonData.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        List personDataList = csvToBean.parse();
        assertNotNull(personDataList.get(0));
        reader.close();
    }
}