package org.ju.cse.cseju.service.syllabus;

import org.ju.cse.cseju.model.syllabus.Course;
import org.ju.cse.cseju.model.syllabus.SyllabusDraft;
import org.ju.cse.cseju.model.syllabus.content.Table;
import org.ju.cse.cseju.model.syllabus.content.TextArea;
import org.ju.cse.cseju.model.syllabus.organizer.ContentDetail;
import org.ju.cse.cseju.model.syllabus.organizer.Semester;
import org.ju.cse.cseju.model.syllabus.organizer.Year;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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

    public void addRowInTable(String databaseName, int contentId) {

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

        SyllabusDraft syllabusDraft = syllabusDraftServices.getSyllabusDraft(syllabusName);

        Year year = syllabusDraft.getYearByYearId(yearId);
        Semester semester = year.getSemesterBySemesterId(semesterId);
        Course course = semester.getCourseByCourseCode(courseCode);

        return course;
    }

    /**
     * @param course
     * @return ContentDetailList from Course
     */
    public List<ContentDetail> getContentDetailList(Course course) {
        ContentDetail[] contentDetails =
                new ContentDetail[course.getTotalNumberOfContents()];

        /**Content Type 0 TextArea*/
        List<TextArea> textAreaList = course.getTextAreaList();
        for (int index = 0; index < textAreaList.size(); index++) {
            int contentIndex = textAreaList.get(index).getTextAreaId();

            /**Content Type 0 = TextArea*/
            contentDetails[contentIndex] = new ContentDetail(
                    0,
                    index
            );
        }

        /**Content Type 1 Table*/
        List<Table> tableList = course.getTableList();
        for (int index = 0; index < tableList.size(); index++) {
            int contentIndex = tableList.get(index).getTableId();

            /**Content Type 1 = Table*/
            contentDetails[contentIndex] = new ContentDetail(
                    1,
                    index
            );
        }

        return Arrays.asList(contentDetails);
    }
}
