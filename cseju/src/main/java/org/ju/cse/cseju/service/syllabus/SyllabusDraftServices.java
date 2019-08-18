package org.ju.cse.cseju.service.syllabus;

import org.ju.cse.cseju.model.syllabus.Course;
import org.ju.cse.cseju.model.syllabus.SyllabusDraft;
import org.ju.cse.cseju.model.syllabus.organizer.Semester;
import org.ju.cse.cseju.model.syllabus.organizer.Year;
import org.ju.cse.cseju.repository.syllabus.SyllabusDraftRepository;
import org.springframework.stereotype.Service;

import java.util.SortedSet;

/**
 * @author Kamrul Hasan
 */
@Service
public class SyllabusDraftServices {

    private CourseTypesServices courseTypesServices = new CourseTypesServices();

    private SyllabusDraftRepository syllabusDraftRepository = new SyllabusDraftRepository();

    /**
     * @param syllabusDraft
     */
    public void createNewSyllabus(SyllabusDraft syllabusDraft) {
        syllabusDraftRepository.saveOrUpdate(syllabusDraft);
        courseTypesServices.createSyllabusCourseTypes(syllabusDraft.getName());
    }

    /**
     * @param syllabusName
     * @return
     */
    public SyllabusDraft getSyllabusDraft(String syllabusName) {
        return syllabusDraftRepository.getSyllabus(syllabusName);
    }

    /**
     * @param syllabusDraft
     */
    public void saveOrUpdate(SyllabusDraft syllabusDraft) {
        syllabusDraftRepository.saveOrUpdate(syllabusDraft);
    }

    /**
     * @param syllabusName
     */
    public void addYear(String syllabusName) {
        SyllabusDraft syllabusDraft = getSyllabusDraft(syllabusName);
        Year year = new Year();

        year.setYearId(syllabusDraft.getMex());
        syllabusDraft.addYear(year);

        saveOrUpdate(syllabusDraft);
    }

    /**
     * Delete a Year By yearId from SyllabusDraft
     *
     * @param syllabusName
     * @param yearId
     */
    public void deleteYear(String syllabusName, Integer yearId) {
        SyllabusDraft syllabusDraft = getSyllabusDraft(syllabusName);

        syllabusDraft.deleteYearByYearId(yearId);

        saveOrUpdate(syllabusDraft);
    }

    /**
     * Add a semester to the Year with yearId = yearId
     *
     * @param syllabusName
     * @param yearId
     */
    public void addSemesterIntoYear(String syllabusName,
                                    Integer yearId) {
        SyllabusDraft syllabusDraft = getSyllabusDraft(syllabusName);

        Year yearUpdated = syllabusDraft.getYearByYearId(yearId);
        yearUpdated.addSemester();
        syllabusDraft.setYearByYearId(yearId, yearUpdated);

        saveOrUpdate(syllabusDraft);
    }


    /**
     * delete Semester by semesterId from Year by yearId
     *
     * @param syllabusName
     * @param yearId
     * @param semesterId
     */
    public void deleteSemesterFromYear(String syllabusName,
                                       Integer yearId,
                                       Integer semesterId) {
        SyllabusDraft syllabusDraft = getSyllabusDraft(syllabusName);

        Year yearUpdated = syllabusDraft.getYearByYearId(yearId);
        yearUpdated.deleteSemesterBySemesterId(semesterId);
        syllabusDraft.setYearByYearId(
                yearId,
                yearUpdated
        );

        saveOrUpdate(syllabusDraft);
    }

    /**
     * @param syllabusName
     * @param yearId
     * @param semesterId
     * @param course
     */
    public void addCourseIntoSemester(String syllabusName,
                                      Integer yearId,
                                      Integer semesterId,
                                      Course course) {
        /**set semesterId an courseId**/
        course.setYearId(yearId);
        course.setSemesterId(semesterId);

        SyllabusDraft syllabusDraft = getSyllabusDraft(syllabusName);

        Year yearUpdated = syllabusDraft.getYearByYearId(yearId);
        Semester semesterUpdated = yearUpdated.getSemesterBySemesterId(semesterId);
        semesterUpdated.setCourseByCourseCode(
                course.getCourseCode(),
                course
        );
        yearUpdated.setSemesterBySemesterId(
                semesterId,
                semesterUpdated
        );
        syllabusDraft.setYearByYearId(
                yearId,
                yearUpdated
        );

        saveOrUpdate(syllabusDraft);
    }


    /**
     * delete a course from a semester
     *
     * @param syllabusName
     * @param yearId
     * @param semesterId
     * @param courseCode
     */
    public void deleteCourseFromSemester(String syllabusName,
                                         Integer yearId,
                                         Integer semesterId,
                                         String courseCode) {
        SyllabusDraft syllabusDraft = getSyllabusDraft(syllabusName);

        Year yearUpdated = syllabusDraft.getYearByYearId(yearId);
        Semester semesterUpdated = yearUpdated.getSemesterBySemesterId(semesterId);
        semesterUpdated.deleteCourseByCourseCode(courseCode);
        yearUpdated.setSemesterBySemesterId(
                semesterId,
                semesterUpdated
        );
        syllabusDraft.setYearByYearId(
                yearId,
                yearUpdated
        );

        saveOrUpdate(syllabusDraft);
    }
}
