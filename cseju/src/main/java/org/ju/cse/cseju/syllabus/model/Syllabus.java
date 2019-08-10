package org.ju.cse.cseju.syllabus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ju.cse.cseju.syllabus.model.organizer.Year;

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
@Getter
@AllArgsConstructor
@XmlRootElement(name = "syllabus")
public class Syllabus {
    private Integer syllabusId;
    private String type;
    private String effectiveYearFrom;
    private String effectiveYearTo;
    private String version;
    private String name;
    private String session;
    private SortedSet<Year> yearList;

    public Syllabus() {
        this.yearList = new TreeSet<>(
                Comparator.comparing(Year::getYearId)
        );
    }

    @XmlAttribute(name = "id")
    public Integer getSyllabusId() {
        return syllabusId;
    }

    @XmlAttribute
    public String getType() {
        return type;
    }

    @XmlAttribute
    public String getSession() {
        return session;
    }

    @XmlElementWrapper(name = "years")
    @XmlElement(name = "year")
    public SortedSet<Year> getYearList() {
        return yearList;
    }

    /**
     * set Session
     */
    public void setSession() {
        this.session = this.effectiveYearFrom +
                "-" + this.effectiveYearTo;
    }

    /**
     * set Name
     */
    public void setName() {
        this.setSession();
        this.name = "syllabus" +
                this.type +
                this.session;
    }
}
