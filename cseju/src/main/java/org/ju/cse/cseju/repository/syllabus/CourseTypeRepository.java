package org.ju.cse.cseju.repository.syllabus;

import org.ju.cse.cseju.model.syllabus.organizer.CourseTypes;

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
public class CourseTypeRepository {

    private final static String EXTENSION = ".xml";
    private final static String STORAGE_LOCATION = "src/main/resources/xml/draft/courseTypes/";

    /**
     * @param databaseName
     * @param courseTypes
     */
    public void saveOrUpdate(String databaseName, CourseTypes courseTypes) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CourseTypes.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File file = new File(STORAGE_LOCATION + databaseName + EXTENSION);
            if (!file.exists()) {
                file.createNewFile();
            }
            marshaller.marshal(courseTypes, file);
            marshaller.marshal(courseTypes, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param databaseName
     * @return
     */
    public CourseTypes getCourseTypes(String databaseName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CourseTypes.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            File file = new File(STORAGE_LOCATION + databaseName + EXTENSION);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
            return (CourseTypes) unmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param syllabusName
     */
    public void create(String syllabusName) {
        CourseTypes courseTypes = new CourseTypes();
        courseTypes.setSyllabusName(syllabusName);
        saveOrUpdate(syllabusName, courseTypes);
    }
}
