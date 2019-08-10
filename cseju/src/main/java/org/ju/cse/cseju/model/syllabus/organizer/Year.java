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
     * @return
     */
    public Integer getMex() {
        if (this.semesterList == null) {
            this.semesterList = new TreeSet<>(
                    Comparator.comparing(Semester::getSemesterId)
            );
        }
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
}
