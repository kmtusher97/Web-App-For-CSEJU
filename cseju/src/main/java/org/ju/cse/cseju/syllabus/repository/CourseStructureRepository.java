package org.ju.cse.cseju.syllabus.repository;

import org.ju.cse.cseju.jaxb.JAXBServices;
import org.ju.cse.cseju.syllabus.model.CourseStructure;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Kamrul Hasan
 */
public class CourseStructureRepository {

    private final static String EXTENSION = ".xml";
    private final static String STORAGE_LOCATION = "src/main/resources/xml/draft/courseStructures/";


    /**
     * @param syllabusName
     * @param courseType
     * @return String databaseName
     */
    private String getDatabaseNameByCourseType(String syllabusName,
                                               String courseType) {
        return syllabusName + "_" + courseType;
    }

    /**
     * @param syllabusName
     * @param courseType
     */
    public void create(String syllabusName, String courseType) {
        String databaseName = getDatabaseNameByCourseType(syllabusName, courseType);
        CourseStructure courseStructure = new CourseStructure(
                courseType,
                databaseName,
                new ArrayList<>()
        );

        try {
            File database = new File(STORAGE_LOCATION + databaseName + EXTENSION);
            if (!database.exists()) {
                database.createNewFile();
            }
            saveOrUpdate(databaseName, courseStructure);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Creates initial xml file with an object of CourseStructure</p>
     *
     * @param databaseName
     */
    public void createInitialXmlDatabase(String databaseName) {
        File file = new File(STORAGE_LOCATION + databaseName + EXTENSION);
        try {
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            JAXBServices jaxbServices = new JAXBServices();

            CourseStructure courseStructureInitial = new CourseStructure();
            courseStructureInitial.setDatabaseName(databaseName);

            fileWriter.write(jaxbServices.objectToXmlString(
                    courseStructureInitial, true
            ));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores an object of CourseStructure class
     *
     * @param dataBaseName
     * @param courseStructure
     */
    public void saveOrUpdate(String dataBaseName, CourseStructure courseStructure) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CourseStructure.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File storageFile = new File(STORAGE_LOCATION + dataBaseName + EXTENSION);

            if (!storageFile.exists()) {
                createInitialXmlDatabase(dataBaseName);
            }

            marshaller.marshal(courseStructure, storageFile);
            marshaller.marshal(courseStructure, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param dataBaseName
     * @return object of CourseStructure class from the xml DB
     */
    public CourseStructure getCourseStructure(String dataBaseName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CourseStructure.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            try {
                File storageFile = new File(STORAGE_LOCATION + dataBaseName + EXTENSION);
                if (!storageFile.exists()) {
                    throw new FileNotFoundException("Database Not Exits!");
                }

                return (CourseStructure) unmarshaller.unmarshal(storageFile);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param syllabusName
     * @param courseType
     */
    public void deleteDatabase(String syllabusName, String courseType) {
        String databaseName = getDatabaseNameByCourseType(syllabusName, courseType);

        File database = new File(STORAGE_LOCATION + databaseName + EXTENSION);
        if (database.exists()) {
            database.delete();
        }
    }
}
