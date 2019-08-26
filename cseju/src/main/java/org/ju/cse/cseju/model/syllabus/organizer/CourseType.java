package org.ju.cse.cseju.model.syllabus.organizer;

import lombok.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author tshr
 */
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "courseType")
public class CourseType {
    private Integer id;
    private String courseTypeName;

    @XmlAttribute
    public Integer getId() {
        return id;
    }

    public String getCourseTypeName() {
        return courseTypeName;
    }
}
