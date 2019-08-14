package org.ju.cse.cseju.model.syllabus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.ju.cse.cseju.model.syllabus.organizer.Year;

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
public class SyllabusDraft {
    private Integer syllabusId;
    private String type;
    private String effectiveYearFrom;
    private String effectiveYearTo;
    private String version;
    private String name;
    private String session;
    private SortedSet<Year> yearList;

    public SyllabusDraft() {
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
        this.name = "syllabus_" +
                this.type +
                this.session;
    }

    /**
     * to handle null pointer exception for null this.yearList
     */
    public void handleNullPointerExceptionOfYearList() {
        if (this.yearList == null) {
            this.yearList = new TreeSet<>(
                    Comparator.comparing(Year::getYearId)
            );
        }
    }

    /**
     * @return mex
     */
    public Integer getMex() {
        handleNullPointerExceptionOfYearList();
        Integer mex = 1;
        for (Year year : this.yearList) {
            if (year.getYearId() == mex) {
                mex++;
            } else if (year.getYearId() > mex) {
                return mex;
            }
        }
        return mex;
    }

    /**
     * @param year
     */
    public void addYear(Year year) {
        handleNullPointerExceptionOfYearList();
        this.yearList.add(year);
    }

    /**
     * delete a year by yearId
     *
     * @param yearId
     */
    public void deleteYear(Integer yearId) {
        handleNullPointerExceptionOfYearList();
        for (Year year : this.yearList) {
            if (year.getYearId() != yearId) {
                continue;
            }
            this.yearList.remove(year);
            break;
        }
    }
}
