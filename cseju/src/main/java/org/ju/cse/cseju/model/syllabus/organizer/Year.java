package org.ju.cse.cseju.model.syllabus.organizer;

import lombok.AllArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 * @author Kamrul Hasan
 */
@Setter
@AllArgsConstructor
@XmlRootElement(name = "year")
public class Year {
    private Integer yearId;
    private SortedSet<Semester> semesterList;

    public Year() {
        this.semesterList = new TreeSet<>(
                Comparator.comparing(Semester::getSemesterId)
        );
    }

    @XmlAttribute(name = "yearId")
    public Integer getYearId() {
        return yearId;
    }

    @XmlElementWrapper(name = "semesters")
    @XmlElement(name = "semester")
    public SortedSet<Semester> getSemesterList() {
        return semesterList;
    }


    /**
     * to handle null pointer exception for null this.semesterList
     */
    public void handleNullPointerExceptionOfSemesterList() {
        if (this.semesterList == null) {
            this.semesterList = new TreeSet<>(
                    Comparator.comparing(Semester::getSemesterId)
            );
        }
    }

    /**
     * @return Integer numberOfCourses
     */
    public Integer getNumberOfCoursesForRowSpan() {
        handleNullPointerExceptionOfSemesterList();
        Integer numberOfCourses = 0;
        for (Semester semester : this.semesterList) {
            numberOfCourses += Math.max(1, semester.getCourseList().size());
        }
        return numberOfCourses;
    }

    /**
     * @return MEX
     */
    public Integer getMex() {
        handleNullPointerExceptionOfSemesterList();
        Integer mex = 1;
        for (Semester semester : this.semesterList) {
            if (semester.getSemesterId() == mex) {
                mex++;
            } else if (semester.getSemesterId() > mex) {
                return mex;
            }
        }
        return mex;
    }

    /**
     * @return String st, nd, rd, th
     */
    public String getYearNameExtension() {
        if (this.yearId % 10 == 1) {
            return "st";
        } else if (this.yearId % 10 == 2) {
            return "nd";
        } else if (this.yearId % 10 == 3) {
            return "rd";
        }
        return "th";
    }

    /**
     * add new Semester
     */
    public void addSemester() {
        handleNullPointerExceptionOfSemesterList();
        Semester semester = new Semester();
        semester.setSemesterId(this.getMex());
        this.semesterList.add(semester);
    }

    /**
     * @param semester
     */
    private void addSemester(Semester semester) {
        handleNullPointerExceptionOfSemesterList();
        this.semesterList.add(semester);
    }

    /**
     * deletes a Semester by semesterId
     *
     * @param semesterId
     */
    public void deleteSemesterBySemesterId(Integer semesterId) {
        handleNullPointerExceptionOfSemesterList();
        for (Semester semester : this.semesterList) {
            if (semester.getSemesterId() != semesterId) continue;
            this.semesterList.remove(semester);
            break;
        }
    }


    /**
     * @param semesterId
     * @return Semester By SemesterId
     */
    public Semester getSemesterBySemesterId(Integer semesterId) {
        handleNullPointerExceptionOfSemesterList();

        Semester semesterResult = null;
        for (Semester semester : this.semesterList) {
            if (semester.getSemesterId() == semesterId) {
                semesterResult = semester;
                break;
            }
        }
        return semesterResult;
    }

    /**
     * @param semesterId
     * @param semesterToSet
     */
    public void setSemesterBySemesterId(Integer semesterId,
                                        Semester semesterToSet) {
        deleteSemesterBySemesterId(semesterId);
        addSemester(semesterToSet);
    }


}
