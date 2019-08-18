package org.ju.cse.cseju.service;

import org.ju.cse.cseju.service.jaxb.JAXBServices;
import org.ju.cse.cseju.model.syllabus.organizer.CourseTypes;
import org.ju.cse.cseju.service.syllabus.CourseTypesServices;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
public class CourseTypesServicesTest {

    private CourseTypesServices courseTypesServices;

    @Before
    public void setUp() throws Exception {
        courseTypesServices = new CourseTypesServices();

        File file = new File("src/main/resources/xml/draft/courseTypes/u18-22.xml");
        file.createNewFile();
        JAXBServices jaxbServices = new JAXBServices();
        String xml = jaxbServices.objectToXmlString(
                new CourseTypes("u18-22", new ArrayList<>()),
                true
        );
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(xml);
        fileWriter.close();
    }

    @After
    public void tearDown() throws Exception {
        File file = new File("src/main/resources/xml/draft/courseTypes/u18-22.xml");
        file.delete();
    }

    @Test
    public void addCourseType() {
        courseTypesServices.addCourseType("u18-22", "Theory");
        courseTypesServices.addCourseType("u18-22", "Laboratory");
    }

    @Test
    public void deleteCourseType() {
        courseTypesServices.addCourseType("u18-22", "Theory");
        courseTypesServices.addCourseType("u18-22", "Laboratory");

        courseTypesServices.deleteCourseType("u18-22", "Theory");
        courseTypesServices.deleteCourseType("u18-22", "Laboratory");
    }
}