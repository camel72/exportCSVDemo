package be.kc.persondata.dao;

import be.kc.persondata.model.PersonData;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.StreamSupport;

@Repository
@Data
public class PersonDataCSVRepository {

    private static final Logger logger = LoggerFactory.getLogger(PersonDataCSVRepository.class);

    private PersonDataRepository personDataRepository;

    public PersonDataCSVRepository(PersonDataRepository personDataRepository) {
        this.personDataRepository = personDataRepository;
    }


    public void uploadFileToDB(File file) throws IOException {
        logger.info("reading file export.csv");

        BufferedReader reader = new BufferedReader(new FileReader(file));

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

        Instant start = Instant.now();
        StreamSupport.stream(csvToBean.spliterator(), true)
                .forEach(personData -> {
                    personDataRepository.save(personData);
                    logger.info("record " + counter.getAndIncrement());
                });
    }
}