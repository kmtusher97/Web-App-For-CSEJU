package org.ju.cse.cseju.model;

import org.ju.cse.cseju.model.syllabus.Course;
import org.ju.cse.cseju.model.syllabus.CourseStructure;
import org.ju.cse.cseju.model.syllabus.content.Content;
import org.ju.cse.cseju.model.syllabus.content.Table;
import org.ju.cse.cseju.model.syllabus.content.TextArea;
import org.ju.cse.cseju.service.syllabus.CourseServices;
import org.ju.cse.cseju.service.syllabus.CourseStructureServices;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class CourseTest {
    /**
     * change the db before running the test
     */
    private final String db = "syllabus_undergrad2018-2022_Theory";

    private CourseStructureServices courseStructureServices;
    private CourseStructure courseStructure;
    private Course course;
    private CourseServices courseServices;

    @Before
    public void setUp() throws Exception {
        course = new Course();
        courseStructureServices = new CourseStructureServices();
        courseStructure = courseStructureServices.getCourseStructure(db);
        courseServices = new CourseServices();


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
        course.initializeWithCourseCourseStructure(courseStructure.getContentBundleList());
        List<TextArea> contentList1 = course.getTextAreaList();
        List<Table> contentList2 = course.getTableList();
        /*assertEquals("Contents", (contentList2.get(0)).getTitle());
        assertEquals("Course Summary", (contentList1.get(0)).getTitle());*/
    }

    @Test
    public void testXmlBinding() {
        course.setInitialField("Test");
        course.initializeWithCourseCourseStructure(courseStructure.getContentBundleList());

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

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Course.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Course course = (Course) unmarshaller.unmarshal(
                    new File("src/main/resources/xml/testCourse.xml")
            );
            System.out.println(course);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getContentList() {
        /**Set the values before running the test*/
        String syllabusName = "syllabus_undergrad2018-2022";
        Integer yearId = 1;
        Integer semesterId = 1;
        String courseCode = "CSE 101";

        course = courseServices
                .getCourseBySyllabusNameAndYearIdAndSemesterIdAndCourseCode(
                        syllabusName,
                        yearId,
                        semesterId,
                        courseCode
                );

        List<Content> contentList = course.getContentList();

        assertEquals(
                course.getTextAreaList().size() +
                        course.getTableList().size(),
                contentList.size()
        );

        for (Content content : contentList) {
            System.err.println(content);
        }
    }

    @Test
    public void setTableList() {
    }
}