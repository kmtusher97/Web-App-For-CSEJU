package org.ju.cse.cseju.syllabus.model;

import lombok.*;
import org.ju.cse.cseju.syllabus.model.content.Content;
import org.ju.cse.cseju.syllabus.model.content.ContentBundle;
import org.ju.cse.cseju.syllabus.model.content.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamrul Hasan
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private String courseCode;
    private String courseTitle;
    private Double courseCredit;
    private String courseType;

    private List<Content> contentList;

    /**
     * Initiates a Course Object from a type
     *
     * @param contentBundleList
     */
    public void initializeCourseCourseStructure(List<ContentBundle> contentBundleList) {
        this.contentList = new ArrayList<>();

        for (ContentBundle contentBundle : contentBundleList) {
            if (contentBundle.getSelected() == 0) {
                this.contentList.add(contentBundle.getTextArea());
            } else if (contentBundle.getSelected() == 1) {
                Table table = contentBundle.getTable();
                table.addRow(0);
                this.contentList.add(table);
            }
        }
    }

    /**
     * @param courseType
     */
    public void setInitialField(String courseType) {
        this.setCourseTitle("Course Title");
        this.setCourseCode("CODE 100");
        this.setCourseCredit(3.0);
        this.setCourseType(courseType);
    }
}
