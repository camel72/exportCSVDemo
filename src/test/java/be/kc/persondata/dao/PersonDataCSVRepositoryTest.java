package be.kc.persondata.dao;

import be.kc.persondata.model.PersonData;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {PersonDataCSVRepository.class})
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.properties")
public class PersonDataCSVRepositoryTest {

    @Value("${export.file.name}")
    private String path;

    @Autowired
    private PersonDataCSVRepository personDataCSVRepository;

    @MockBean
    private PersonDataRepository personDataRepository;


    @Test
    public void uploadFileToDBMainSuccessScenarioTest() throws Exception {
        personDataCSVRepository.uploadFileToDB(new File(path));

        verify(personDataRepository, atLeastOnce()).save(any());
    }

    @Disabled
    @Test
    public void uploadFileToDBRecordExistsInDB() throws Exception {
        List<PersonData> personDataList = Lists.newArrayList();
        personDataList.add(new PersonData());
        when(personDataRepository.findByLastNameAndFirstNameAndBirthDate(
                anyString(), anyString(), any(LocalDate.class)))
                .thenReturn(personDataList);

        personDataCSVRepository.uploadFileToDB(new File(path));

        verify(personDataRepository, atLeastOnce()).findByLastNameAndFirstNameAndBirthDate("testLastName", "testFirstName", LocalDate.of(2000, 1, 1));
        verify(personDataRepository, never()).save(any());
    }
}