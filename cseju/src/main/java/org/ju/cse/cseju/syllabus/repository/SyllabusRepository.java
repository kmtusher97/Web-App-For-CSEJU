package org.ju.cse.cseju.syllabus.repository;

import org.ju.cse.cseju.syllabus.model.Syllabus;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Kamrul Hasan
 */
public class SyllabusRepository {

    private final static String EXTENSION = ".xml";
    private final static String STORAGE_LOCATION = "src/main/resources/xml/draft/syllabusDraft/";

    /**
     * @param syllabus
     */
    public void saveOrUpdate(Syllabus syllabus) {
        try {
            File database = new File(
                    STORAGE_LOCATION + syllabus.getName() + EXTENSION
            );
            if (!database.exists()) {
                database.createNewFile();
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(Syllabus.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(syllabus, database);
            marshaller.marshal(syllabus, System.out);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param databaseName
     * @return
     */
    public Syllabus getSyllabus(String databaseName) {
        try {
            File database = new File(
                    STORAGE_LOCATION + databaseName + EXTENSION
            );
            if (!database.exists()) {
                throw new FileNotFoundException("Database doesn't exists!!!");
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(Syllabus.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (Syllabus) unmarshaller.unmarshal(database);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param databaseName
     */
    public void delete(String databaseName) {
        try {
            File database = new File(
                    STORAGE_LOCATION + databaseName + EXTENSION
            );
            if (database.exists()) {
                database.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
