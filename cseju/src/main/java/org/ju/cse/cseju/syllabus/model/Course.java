package org.ju.cse.cseju.syllabus.model;

import lombok.*;
import org.ju.cse.cseju.syllabus.model.content.ContentBundle;
import org.ju.cse.cseju.syllabus.model.content.Table;
import org.ju.cse.cseju.syllabus.model.content.TextArea;

import javax.xml.bind.annotation.*;
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
@XmlRootElement(name = "course")
@XmlType(propOrder = {
        "courseTitle", 
        "courseCredit",
        "courseType",
        "textAreaList",
        "tableList"
})
public class Course {
    private String courseCode;
    private String courseTitle;
    private Double courseCredit;
    private String courseType;

    private List<TextArea> textAreaList;
    private List<Table> tableList;


    @XmlAttribute(name = "courseCode")
    public String getCourseCode() {
        return courseCode;
    }

    @XmlElement(name = "content")
    public List<TextArea> getTextAreaList() {
        return textAreaList;
    }

    @XmlElement(name = "content")
    public List<Table> getTableList() {
        return tableList;
    }

/*@XmlElementWrapper(name = "contents")
    @XmlMixed
    public List<Content> getContentList() {
        return contentList;
    }*/

    /**
     * Initiates a Course Object from a type
     *
     * @param contentBundleList
     */
    public void initializeCourseCourseStructure(List<ContentBundle> contentBundleList) {
        this.textAreaList = new ArrayList<>();
        this.tableList = new ArrayList<>();

        Integer contentID = 1;
        for (ContentBundle contentBundle : contentBundleList) {
            if (contentBundle.getSelected() == 0) {
                TextArea textArea = contentBundle.getTextArea();
                textArea.setTextAreaId(contentID++);
                this.textAreaList.add(textArea);
            } else if (contentBundle.getSelected() == 1) {
                Table table = contentBundle.getTable();
                table.setTableId(contentID++);
                table.addRow(0);
                this.tableList.add(table);
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
