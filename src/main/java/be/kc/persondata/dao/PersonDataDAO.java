package be.kc.persondata.dao;

import be.kc.persondata.model.PersonData;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Repository
@Data
public class PersonDataDAO {

    public List<PersonData> getPersonData(String lastName) throws Exception {
        return personDataCSVBuilder();
    }


    public List<PersonData> personDataCSVBuilder() throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource("export.csv").toURI());

        //CSvtr csvTransfer = new CsvTransfer();
        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
        ms.setType(PersonData.class);

        Reader reader = Files.newBufferedReader(path);
        CsvToBean cb = new CsvToBeanBuilder(reader)
                .withType(PersonData.class)
                .withMappingStrategy(ms)
                .build();

        //csvTransfer.setCsvList(cb.parse());
        reader.close();
        //return csvTransfer.getCsvList();
        return cb.parse();
    }
}