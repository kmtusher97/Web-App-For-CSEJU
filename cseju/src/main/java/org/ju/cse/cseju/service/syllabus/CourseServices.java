package org.ju.cse.cseju.service.syllabus;

import org.ju.cse.cseju.model.syllabus.Course;
import org.ju.cse.cseju.model.syllabus.SyllabusDraft;
import org.ju.cse.cseju.model.syllabus.organizer.Semester;
import org.ju.cse.cseju.model.syllabus.organizer.Year;
import org.springframework.stereotype.Service;

/**
 * @author Kamrul Hasan
 */
@Service
public class CourseServices {

    private CourseStructureServices courseStructureServices = new CourseStructureServices();

    private SyllabusDraftServices syllabusDraftServices = new SyllabusDraftServices();

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
        course.initializeWithCourseCourseStructure(
                courseStructureServices.getCourseStructure(
                        getDatabaseName(courseType)
                ).getContentBundleList()
        );
        return course;
    }


    /**
     * @param syllabusName
     * @param yearId
     * @param semesterId
     * @param courseCode
     * @return Course Input Form
     */
    public Course getCourseBySyllabusNameAndYearIdAndSemesterIdAndCourseCode(
            String syllabusName,
            Integer yearId,
            Integer semesterId,
            String courseCode
    ) {

        SyllabusDraft syllabusDraft =
                syllabusDraftServices.getSyllabusDraft(syllabusName);

        Year year = syllabusDraft.getYearByYearId(yearId);
        Semester semester = year.getSemesterBySemesterId(semesterId);
        Course course = semester.getCourseByCourseCode(courseCode);

        return course;
    }

    /**
     * @param syllabusName
     * @param yearId
     * @param semesterId
     * @param courseCode
     * @param contentId
     */
    public void addRowInTableByTableId(
            String syllabusName,
            Integer yearId,
            Integer semesterId,
            String courseCode,
            Integer contentId
    ) {

        SyllabusDraft syllabusDraft =
                syllabusDraftServices.getSyllabusDraft(syllabusName);

        Year year = syllabusDraft.getYearByYearId(yearId);
        Semester semester = year.getSemesterBySemesterId(semesterId);
        Course course = semester.getCourseByCourseCode(courseCode);
        course.addInputRowInTableByTableId(contentId);
        semester.setCourseByCourseCode(courseCode, course);
        year.setSemesterBySemesterId(semesterId, semester);
        syllabusDraft.setYearByYearId(yearId, year);

        syllabusDraftServices.saveOrUpdate(syllabusDraft);
    }


    /**
     * @param syllabusName
     * @param yearId
     * @param semesterId
     * @param courseCode
     * @param contentId
     * @param rowIndex
     */
    public void deleteInputRowFromTableByRowIndex(
            String syllabusName,
            Integer yearId,
            Integer semesterId,
            String courseCode,
            Integer contentId,
            int rowIndex
    ) {
        SyllabusDraft syllabusDraft =
                syllabusDraftServices.getSyllabusDraft(syllabusName);

        Year year = syllabusDraft.getYearByYearId(yearId);
        Semester semester = year.getSemesterBySemesterId(semesterId);
        Course course = semester.getCourseByCourseCode(courseCode);
        course.deleteInputRowFromTableByRowIndex(contentId, rowIndex);
        semester.setCourseByCourseCode(courseCode, course);
        year.setSemesterBySemesterId(semesterId, semester);
        syllabusDraft.setYearByYearId(yearId, year);

        syllabusDraftServices.saveOrUpdate(syllabusDraft);
    }


}
