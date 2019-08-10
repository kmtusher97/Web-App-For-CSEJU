package org.ju.cse.cseju.model.syllabus.organizer;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Kamrul Hasan
 */
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "courseTypes")
public class CourseTypes {
    private String syllabusName;
    private List<String> courseTypeList;

    @XmlAttribute
    public String getSyllabusName() {
        return syllabusName;
    }

    @XmlElement(name = "courseType")
    public List<String> getCourseTypeList() {
        return courseTypeList;
    }
}
