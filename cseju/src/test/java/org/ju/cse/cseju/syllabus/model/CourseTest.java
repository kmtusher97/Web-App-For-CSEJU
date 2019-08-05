package org.ju.cse.cseju.syllabus.model;

import org.ju.cse.cseju.syllabus.model.content.Content;
import org.ju.cse.cseju.syllabus.model.content.Table;
import org.ju.cse.cseju.syllabus.model.content.TextArea;
import org.ju.cse.cseju.syllabus.service.CourseStructureServices;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

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
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void initializeCourseCourseStructure() {
        course.initializeCourseCourseStructure(courseStructure.getContentBundleList());
        List<Content> contentList = course.getContentList();
        assertEquals("Contents", ((Table) contentList.get(0)).getTitle());
        assertEquals("Course Summary", ((TextArea) contentList.get(1)).getTitle());
    }
}