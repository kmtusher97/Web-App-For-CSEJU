package org.ju.cse.cseju.syllabus.model.organizer;

import java.io.Serializable;
import java.util.SortedSet;

/**
 * @author Kamrul Hasan
 */
public class Year implements Serializable {

    private Integer yearNo;

    private SortedSet<Semester> semesters;
}
