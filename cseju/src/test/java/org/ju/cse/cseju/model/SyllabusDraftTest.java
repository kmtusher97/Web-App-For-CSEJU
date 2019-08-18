package org.ju.cse.cseju.model;

import org.ju.cse.cseju.model.syllabus.SyllabusDraft;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.TreeSet;

public class SyllabusDraftTest {

    private SyllabusDraft syllabusDraft;

    @Before
    public void setUp() throws Exception {
        syllabusDraft = new SyllabusDraft();
        syllabusDraft.setSyllabusId(1);
        syllabusDraft.setName("sylU2018-22");
        syllabusDraft.setType("U");
        syllabusDraft.setEffectiveYearFrom("2018");
        syllabusDraft.setEffectiveYearTo("2022");
        syllabusDraft.setVersion("1.0");
        syllabusDraft.setSession();
        syllabusDraft.setYearList(new TreeSet<>());

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
            JAXBContext jaxbContext = JAXBContext.newInstance(SyllabusDraft.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(syllabusDraft, new File("src/main/resources/xml/draft/test.xml"));
            marshaller.marshal(syllabusDraft, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}