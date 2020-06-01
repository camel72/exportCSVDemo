package be.kc.persondata.dao;

import be.kc.persondata.model.PersonData;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Repository
@Data
public class PersonDataCSVRepository {

    private static final Logger logger = LoggerFactory.getLogger(PersonDataCSVRepository.class);

    private PersonDataRepository personDataRepository;

    public PersonDataCSVRepository(PersonDataRepository personDataRepository) {
        this.personDataRepository = personDataRepository;
    }


    public void uploadFileToDB() throws Exception {
        Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource("export.csv").toURI()), StandardCharsets.ISO_8859_1);

        CsvToBean<PersonData> csvToBean = new CsvToBeanBuilder(reader)
                .withType(PersonData.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        Instant start = null;
        try {
            start = Instant.now();
            csvToBean.stream().parallel().forEach(personData -> {
                int counter = 0;
                logger.info("record " + ++counter);
                personDataRepository.save(personData);
            });
        } finally {
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMinutes();
            logger.info(String.format("loading file 2 DB took %s minutes", timeElapsed));
            reader.close();
        }
    }
}