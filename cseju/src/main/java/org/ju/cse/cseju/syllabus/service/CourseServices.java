package org.ju.cse.cseju.syllabus.service;

import org.ju.cse.cseju.syllabus.model.Course;
import org.springframework.stereotype.Service;

/**
 * @author Kamrul Hasan
 */
@Service
public class CourseServices {

    private CourseStructureServices courseStructureServices = new CourseStructureServices();

    /**
     * @param courseType
     * @return
     */
    public String getDatabaseName(String courseType) {
        return courseType; //initial ,, will be changed
    }

    /**
     * @param courseType
     * @return Course
     */
    public Course getCourseInputForm(String courseType) {
        Course course = new Course();
        course.setInitialField(courseType);
        course.initializeCourseCourseStructure(
                courseStructureServices.getCourseStructure(
                        getDatabaseName(courseType)
                ).getContentBundleList()
        );
        return course;
    }

    public void addRowInTable(String databaseName, int contentId) {

    }

    public Course getCourseInputForm(String databaseName, String courseCode) {
        return null;
    }
}
