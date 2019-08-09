package org.ju.cse.cseju.syllabus.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class CourseStructureServicesTest {

    private CourseStructureServices courseStructureServices;

    @Before
    public void setUp() throws Exception {
        courseStructureServices = new CourseStructureServices();
    }

    @After
    public void tearDown() throws Exception {

        File file = new File("src/main/resources/xml/draft/courseStructures/u18-22_theory.xml");
        file.delete();
    }

    @Test
    public void createNewCourseStructure() {
        courseStructureServices.createNewCourseStructure("u18-22", "theory");
        assertNotEquals(null, new File("src/main/resources/xml/draft/courseStructures/u18-22_theory.xml"));
    }

    @Test
    public void saveOrUpdate() {
    }

    @Test
    public void getCourseStructure() {
    }

    @Test
    public void addContentBundle() {
    }

    @Test
    public void deleteContentBundleByContentBundleId() {
    }

    @Test
    public void addFieldNameIntoTableByContentBundleId() {
    }

    @Test
    public void deleteFieldNameFromTableByContentBundleId() {
    }

    @Test
    public void getContentListForPreview() {
    }
}