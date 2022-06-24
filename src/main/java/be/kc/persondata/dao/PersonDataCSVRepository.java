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
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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
            processCSVToPersonData(csvToBeanPersonData);
        } finally {
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMinutes();
            logger.info(String.format("loading file into the DB took %s minutes", timeElapsed));
            reader.close();
        }
    }

    private void processCSVToPersonData(CsvToBean<PersonData> csvToBean) throws IOException {
        AtomicInteger counter = new AtomicInteger(0);
        logger.info("starting to process file export.csv");

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        csvToBean.forEach(personData -> {
            executor.execute(() -> savePersonData(counter, personData));
        });
    }

    private void savePersonData(AtomicInteger counter, PersonData personData) {
        personDataRepository.save(personData);
        logger.info("record " + counter.getAndIncrement());
    }
}