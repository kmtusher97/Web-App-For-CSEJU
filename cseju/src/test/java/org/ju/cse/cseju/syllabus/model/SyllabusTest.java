package org.ju.cse.cseju.syllabus.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class SyllabusTest {

    private Syllabus syllabus;

    @Before
    public void setUp() throws Exception {
        syllabus = new Syllabus();
        syllabus.setSyllabusId(1);
        syllabus.setName("sylU2018-22");
        syllabus.setType("U");
        syllabus.setEffectiveYearFrom("2018");
        syllabus.setEffectiveYearTo("2022");
        syllabus.setVersion("1.0");
        syllabus.setSession();
        syllabus.setYearList(new TreeSet<>());

        File file = new File("src/main/resources/xml/draft/test.xml");
        file.createNewFile();
    }

    @After
    public void tearDown() throws Exception {
        File file = new File("src/main/resources/xml/draft/test.xml");
        file.delete();
    }

    @Test
    public void testXmlBinding() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Syllabus.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(syllabus, new File("src/main/resources/xml/draft/test.xml"));
            marshaller.marshal(syllabus, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}