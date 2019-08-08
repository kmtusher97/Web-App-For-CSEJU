package org.ju.cse.cseju.syllabus.model.organizer;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.ju.cse.cseju.syllabus.model.Course;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Kamrul Hasan
 */
@Setter
@AllArgsConstructor
@XmlRootElement(name = "semester")
public class Semester implements Serializable {

    private Integer semesterNo;

    private SortedSet<Course> courses;

    public Semester() {
        this.courses = new TreeSet<>(
                Comparator.comparing(Course::parseCourseCodeNumber)
        );
    }

    @XmlAttribute(name = "semesterNo")
    public Integer getSemesterNo() {
        return semesterNo;
    }

    @XmlElementWrapper(name = "courses")
    @XmlElement(name = "course")
    public SortedSet<Course> getCourses() {
        return courses;
    }

    /**
     * @param course
     */
    public void addCourse(Course course) {
        Course courseDelete = new Course();
        for (Course course1 : this.courses) {
            if (!course1.getCourseCode().equals(course.getCourseCode())) {
                continue;
            }
            courseDelete = course1;
            break;
        }
        this.courses.remove(courseDelete);
        this.courses.add(course);
    }

    /**
     * @param course
     */
    public void removeCourse(Course course) {
        this.courses.remove(course);
    }
}
