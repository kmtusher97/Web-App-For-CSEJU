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
        syllabusDraft.deleteYear(yearId);
        saveOrUpdate(syllabusDraft);
    }

    /**
     * Add a semester to the Year with yearId = yearId
     *
     * @param syllabusName
     * @param yearId
     */
    public void addSemesterIntoYear(String syllabusName, Integer yearId) {
        SyllabusDraft syllabusDraft = getSyllabusDraft(syllabusName);
        SortedSet<Year> years = syllabusDraft.getYearList();

        for (Year year : years) {
            if (year.getYearId() != yearId) continue;
            year.addSemester();
            break;
        }

        syllabusDraft.setYearList(years);
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
                                       Integer semesterId
    ) {
        SyllabusDraft syllabusDraft = getSyllabusDraft(syllabusName);
        SortedSet<Year> years = syllabusDraft.getYearList();

        for (Year year : years) {
            if (year.getYearId() != yearId) continue;
            year.deleteSemester(semesterId);
            break;
        }

        syllabusDraft.setYearList(years);
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

        SyllabusDraft syllabusDraft = getSyllabusDraft(syllabusName);
        SortedSet<Year> years = syllabusDraft.getYearList();

        Year yearUpdate = null;

        for (Year year : years) {
            if (year.getYearId() != yearId) continue;
            yearUpdate = year;
            break;
        }
        if (yearUpdate != null) {
            years.remove(yearUpdate);

            SortedSet<Semester> semesters = yearUpdate.getSemesterList();
            for (Semester semester : semesters) {
                if (semester.getSemesterId() != semesterId) continue;
                semester.addCourse(course);
                break;
            }

            yearUpdate.setSemesterList(semesters);
            years.add(yearUpdate);
        }

        syllabusDraft.setYearList(years);
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
        SortedSet<Year> years = syllabusDraft.getYearList();

        Year yearUpdate = null;

        for (Year year : years) {
            if (year.getYearId() != yearId) continue;
            yearUpdate = year;
            break;
        }
        if (yearUpdate != null) {
            years.remove(yearUpdate);

            SortedSet<Semester> semesters = yearUpdate.getSemesterList();
            for (Semester semester : semesters) {
                if (semester.getSemesterId() != semesterId) continue;
                semester.deleteCourse(courseCode);
                break;
            }

            yearUpdate.setSemesterList(semesters);
            years.add(yearUpdate);
        }

        syllabusDraft.setYearList(years);
        saveOrUpdate(syllabusDraft);
    }
}
