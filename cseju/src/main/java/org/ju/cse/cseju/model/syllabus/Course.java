package org.ju.cse.cseju.model.syllabus;

import lombok.*;
import org.ju.cse.cseju.model.syllabus.content.Content;
import org.ju.cse.cseju.model.syllabus.content.ContentBundle;
import org.ju.cse.cseju.model.syllabus.content.Table;
import org.ju.cse.cseju.model.syllabus.content.TextArea;
import org.ju.cse.cseju.model.syllabus.organizer.ContentDetail;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        "semesterId",
        "yearId",
        "textAreaList",
        "tableList"
})
public class Course {
    private String courseCode;
    private String courseTitle;
    private Double courseCredit;
    private String courseType;
    private Integer semesterId;
    private Integer yearId;

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

    /**
     * If the List fields are null
     */
    public void handleNullPointerExceptionForListFields() {
        if (this.textAreaList == null) {
            this.textAreaList = new ArrayList<>();
        }
        if (this.tableList == null) {
            this.tableList = new ArrayList<>();
        }
    }

    /**
     * @return <p><b>int</b> total number of contents</p>
     */
    public int getTotalNumberOfContents() {
        handleNullPointerExceptionForListFields();

        return (this.textAreaList.size() +
                this.tableList.size());
    }

    /**
     * Initiates a Course Object from a type
     *
     * @param contentBundleList
     */
    public void initializeWithCourseCourseStructure(List<ContentBundle> contentBundleList) {
        handleNullPointerExceptionForListFields();

        Integer contentID = 0;
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

    /**
     * parses the integer from courseCode
     *
     * @return
     */
    public Integer getCourseCodeNumber() {
        if (this.courseCode == null) {
            this.courseCode = "";
        }
        Integer codeNumber = 0;
        Integer powerOf10 = 1;

        for (int i = this.courseCode.length() - 1; i >= 0; i--) {
            if (this.courseCode.charAt(i) >= '0' &&
                    this.courseCode.charAt(i) <= '9') {
                codeNumber += (
                        (this.courseCode.charAt(i) - '0') * powerOf10
                );
                powerOf10 *= 10;
            }
        }
        return codeNumber;
    }

    /**
     * @return <p>List<Content> ot Table & TextArea</p>
     */
    public List<Content> getContentList() {
        handleNullPointerExceptionForListFields();

        Content[] contents =
                new Content[getTotalNumberOfContents()];

        for (TextArea textArea : this.textAreaList) {
            int contentId = textArea.getTextAreaId();
            contents[contentId] = textArea;
        }

        for (Table table : this.tableList) {
            int contentId = table.getTableId();
            contents[contentId] = table;
        }

        return Arrays.asList(contents);
    }
}
