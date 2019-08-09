package org.ju.cse.cseju.syllabus.model;

import org.ju.cse.cseju.syllabus.model.content.Table;
import org.ju.cse.cseju.syllabus.model.content.TextArea;
import org.ju.cse.cseju.syllabus.service.CourseStructureServices;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class CourseTest {
    private final String db = "test";

    private CourseStructureServices courseStructureServices;
    private CourseStructure courseStructure;
    private Course course;

    @Before
    public void setUp() throws Exception {
        course = new Course();
        courseStructureServices = new CourseStructureServices();
        courseStructure = courseStructureServices.getCourseStructure(db);

        File file = new File("src/main/resources/xml/testCourse.xml");
        file.createNewFile();
    }

    @After
    public void tearDown() throws Exception {
        File file = new File("src/main/resources/xml/testCourse.xml");
        file.delete();
    }

    @Test
    public void initializeCourseCourseStructure() {
        course.initializeCourseCourseStructure(courseStructure.getContentBundleList());
        List<TextArea> contentList1 = course.getTextAreaList();
        List<Table> contentList2 = course.getTableList();
        assertEquals("Contents", (contentList2.get(0)).getTitle());
        assertEquals("Course Summary", (contentList1.get(0)).getTitle());
    }

    @Test
    public void testXmlBinding() {
        course.setInitialField("Test");
        course.initializeCourseCourseStructure(courseStructure.getContentBundleList());

        /*List<Content> contentList = course.getContentList();
        for(Content content : contentList) {
            System.err.println(content);
        }*/

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Course.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(course, new File("src/main/resources/xml/testCourse.xml"));
            marshaller.marshal(course, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}