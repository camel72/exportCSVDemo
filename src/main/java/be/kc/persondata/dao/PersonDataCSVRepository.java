package be.kc.persondata.dao;

import be.kc.persondata.model.PersonData;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Repository
@Data
public class PersonDataCSVRepository {

    public PersonData getPersonData(String lastName) throws Exception {
        final List<PersonData> personData = getPersonDatas();
        // TODO implementation
        return null;
    }


    public List<PersonData> getPersonDatas() throws Exception {
        Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource("export.csv").toURI()));

        CsvToBean<PersonData> csvToBean = new CsvToBeanBuilder(reader)
                .withType(PersonData.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        List<PersonData> personDataList = csvToBean.parse();
        reader.close();

        return personDataList;
    }
}