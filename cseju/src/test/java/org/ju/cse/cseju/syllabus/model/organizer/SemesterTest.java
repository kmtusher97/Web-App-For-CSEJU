package org.ju.cse.cseju.syllabus.model.organizer;

import org.ju.cse.cseju.jaxb.JAXBServices;
import org.ju.cse.cseju.syllabus.model.Course;
import org.ju.cse.cseju.syllabus.model.content.Content;
import org.ju.cse.cseju.syllabus.model.content.Table;
import org.ju.cse.cseju.syllabus.model.content.TextArea;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SemesterTest {

    private Semester semester;

    private JAXBServices jaxbServices;

    @Before
    public void setUp() throws Exception {
        jaxbServices = new JAXBServices();
        semester = new Semester();
        Course course = new Course();
        course.setCourseCode("Math 101");
        List<Object> contents = new ArrayList<>();
        //contents.add((new TextArea()).getInitialTextArea(0));
        //contents.add((new Table()).getInitialTable(0));
        course.setContentList(contents);
        semester.addCourse(course);

        /*Course course1 = new Course();
        course1.setCourseCode("CSE 100");
        semester.addCourse(course1);

        Course course2 = new Course();
        course2.setCourseCode("CSE 120");
        semester.addCourse(course2);*/

        semester.setSemesterNo(2);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSortedSet() {
//        for(Course course1 : semester.getCourses()) {
//            System.out.println(course1.getCourseCode());
//        }

        System.out.println(jaxbServices.objectToXmlString(semester, true));
    }
}