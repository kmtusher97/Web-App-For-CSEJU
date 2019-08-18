package org.ju.cse.cseju.repository.syllabus;

import org.ju.cse.cseju.model.syllabus.SyllabusDraft;

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
public class SyllabusDraftRepository {

    private final static String EXTENSION = ".xml";
    private final static String STORAGE_LOCATION = "src/main/resources/xml/draft/syllabusDraft/";

    /**
     * @param syllabusDraft
     */
    public void saveOrUpdate(SyllabusDraft syllabusDraft) {
        try {
            File database = new File(
                    STORAGE_LOCATION + syllabusDraft.getName() + EXTENSION
            );
            if (!database.exists()) {
                database.createNewFile();
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(SyllabusDraft.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(syllabusDraft, database);
            marshaller.marshal(syllabusDraft, System.out);

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
    public SyllabusDraft getSyllabus(String databaseName) {
        try {
            File database = new File(
                    STORAGE_LOCATION + databaseName + EXTENSION
            );
            if (!database.exists()) {
                throw new FileNotFoundException("Database doesn't exists!!!");
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(SyllabusDraft.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (SyllabusDraft) unmarshaller.unmarshal(database);

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
