package be.kc.persondata.controller.v1;

import be.kc.persondata.model.PersonData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
class PersonDataRestControllerTest {

    private static final String SERVER_URL = "http://localhost:";
    private static String CONTEXT_URL = "/api/v1/persondata/";

    @Autowired
    PersonDataRestController controller;

    @Value("${export.file.name}")
    private String path;

    @Value("${spring.servlet.multipart.location}")
    private String UPLOAD_DIR;


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> responseLoad;


    @BeforeEach
    public void init() throws IOException {
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("file", new FileSystemResource(new File(path)));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

        responseLoad = restTemplate.exchange(SERVER_URL + port + CONTEXT_URL + "load",
                HttpMethod.POST, requestEntity, String.class);
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