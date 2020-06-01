package be.kc.persondata.controller.v1;

import be.kc.persondata.model.PersonData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PersonDataControllerTest {

    private static final String SERVER_URL = "http://localhost:";
    private static String CONTEXT_URL = "/api/v1/persondata/";

    @Autowired
    PersonDataController controller;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> responseLoad;


    @BeforeEach
    public void init() {
        responseLoad = restTemplate.getForEntity(SERVER_URL + port + CONTEXT_URL + "load"
                , String.class);
    }

    @Test
    void testLoad() {
        assertThat(responseLoad).isNotNull();
        assertEquals(HttpStatus.OK, responseLoad.getStatusCode());
    }

    @Test
    void testFindByLastName() {
        ResponseEntity<PersonData[]> personData = restTemplate.getForEntity(
                SERVER_URL + port + CONTEXT_URL + "lastname/testLastName", PersonData[].class
        );

        assertThat(personData).isNotNull();
        PersonData[] body = personData.getBody();
        assertEquals("testLastName", body[0].getLastName());
        assertEquals("testFirstName", body[0].getFirstName());
    }
}