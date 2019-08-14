package org.ju.cse.cseju.model.syllabus.organizer;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ju.cse.cseju.model.syllabus.Course;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Kamrul Hasan
 */
@Setter
@ToString
@AllArgsConstructor
@XmlRootElement(name = "semester")
public class Semester {
    private Integer semesterId;
    private SortedSet<Course> courseList;

    public Semester() {
        this.courseList = new TreeSet<>(
                Comparator.comparing(Course::getCourseCodeNumber)
        );
    }

    @XmlAttribute(name = "semesterId")
    public Integer getSemesterId() {
        return semesterId;
    }

    @XmlElementWrapper(name = "courses")
    @XmlElement(name = "course")
    public SortedSet<Course> getCourseList() {
        return courseList;
    }


    /**
     * @return String
     */
    public String getSemesterNameExtension() {
        if (this.semesterId % 10 == 1) {
            return "st";
        } else if (this.semesterId % 10 == 2) {
            return "nd";
        } else if (this.semesterId % 10 == 3) {
            return "rd";
        }
        return "th";
    }

    /**
     * to handle null pointer exception for null this.courseList
     */
    public void handleNullPointerExceptionOfCourseList() {
        if (this.courseList == null) {
            this.courseList = new TreeSet<>(
                    Comparator.comparing(Course::getCourseCodeNumber)
            );
        }
    }

    /**
     * add course
     *
     * @param course
     */
    public void addCourse(Course course) {
        handleNullPointerExceptionOfCourseList();
        this.courseList.add(course);
    }

    /**
     * delete a course by courseCode
     *
     * @param courseCode
     */
    public void deleteCourse(String courseCode) {
        handleNullPointerExceptionOfCourseList();
        for (Course course : this.courseList) {
            if (!course.getCourseCode().equals(courseCode)) continue;
            this.courseList.remove(course);
            break;
        }
    }
}
