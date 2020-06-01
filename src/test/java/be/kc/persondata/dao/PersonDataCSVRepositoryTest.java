package be.kc.persondata.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;

@ContextConfiguration(classes = {PersonDataCSVRepository.class})
@ExtendWith(SpringExtension.class)
public class PersonDataCSVRepositoryTest {

    @Autowired
    private PersonDataCSVRepository personDataCSVRepository;

    @MockBean
    private PersonDataRepository personDataRepository;


    @Test
    public void uploadFileToDBTest() throws Exception {
        Mockito.when(personDataRepository.save(any()))
                .thenReturn(any());

        personDataCSVRepository.uploadFileToDB();

        verify(personDataRepository, atLeastOnce()).save(any());
    }
}