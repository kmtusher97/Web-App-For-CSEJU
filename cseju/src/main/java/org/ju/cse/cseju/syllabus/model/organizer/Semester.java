package org.ju.cse.cseju.syllabus.model.organizer;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ju.cse.cseju.syllabus.model.Course;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Kamrul Hasan
 */
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "semester")
public class Semester {
    private Integer semesterId;
    private List<Course> courseList;

    @XmlAttribute(name = "semesterId")
    public Integer getSemesterId() {
        return semesterId;
    }

    @XmlElementWrapper(name = "courses")
    @XmlElement(name = "course")
    public List<Course> getCourseList() {
        return courseList;
    }
}
