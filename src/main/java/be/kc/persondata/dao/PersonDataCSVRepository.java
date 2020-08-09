package be.kc.persondata.dao;

import be.kc.persondata.model.PersonData;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.StreamSupport;

@Repository
@Data
public class PersonDataCSVRepository {

    private static final Logger logger = LoggerFactory.getLogger(PersonDataCSVRepository.class);

    //private static final String FILE_NAME = "export_full.csv";
    //private static final String FILE_NAME = "export.csv";

    private PersonDataRepository personDataRepository;

    public PersonDataCSVRepository(PersonDataRepository personDataRepository) {
        this.personDataRepository = personDataRepository;
    }


    public void uploadFileToDB(String fileName) throws Exception {
        logger.info("reading file export.csv");
         Reader reader = new InputStreamReader(new ClassPathResource(fileName).getInputStream());
        // Doesn't work when running the application from the packaged file (jar).
//        Reader reader = Files.newBufferedReader(Paths.get(
//                ClassLoader.getSystemResource(fileName).toURI()), StandardCharsets.ISO_8859_1);

        CsvToBean<PersonData> csvToBeanPersonData = new CsvToBeanBuilder(reader)
                .withType(PersonData.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        Instant start = null;
        try {
            start = Instant.now();
            processCSVToPersonData(reader, csvToBeanPersonData);
        } finally {
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMinutes();
            logger.info(String.format("loading file into the DB took %s minutes", timeElapsed));
            reader.close();
        }
    }

    private void processCSVToPersonData(Reader reader, CsvToBean<PersonData> csvToBean) throws IOException {
        AtomicInteger counter = new AtomicInteger(0);
        logger.info("starting to process file export.csv");

        StreamSupport.stream(csvToBean.spliterator(), false).forEach(personData -> {
            List<PersonData> personDataList = personDataRepository.findByLastNameAndFirstNameAndBirthDate(
                    personData.getLastName(), personData.getFirstName(), personData.getBirthDate());
            logger.info(String.format("processing personData: %s", personData.toString()));
            logger.info("record " + counter.getAndIncrement());
            if (personDataList.isEmpty()) {
                logger.info("saving record " + counter.getAndIncrement());
                personDataRepository.save(personData);
            } else {
                logger.info(String.format("record %s already exists in DB", personData.toString()));
            }
        });
    }
}