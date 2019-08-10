package org.ju.cse.cseju.repository;

import org.ju.cse.cseju.repository.syllabus.CourseStructureRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CourseStructureRepositoryTest {

    private CourseStructureRepository courseStructureRepository;

    @Before
    public void setUp() throws Exception {
        courseStructureRepository = new CourseStructureRepository();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void saveOrUpdate() {
    }

    @Test
    public void getCourseInputForm() {
    }

    @Test
    public void createInitialXmlDatabase() {
        courseStructureRepository.createInitialXmlDatabase("test");
    }
}