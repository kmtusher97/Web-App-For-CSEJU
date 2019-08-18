package org.ju.cse.cseju.model.syllabus;

import lombok.*;
import org.ju.cse.cseju.model.syllabus.content.ContentBundle;
import org.ju.cse.cseju.model.syllabus.content.Table;
import org.ju.cse.cseju.model.syllabus.content.TextArea;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
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
@XmlRootElement(name = "courseStructure")
@XmlType(propOrder = {"name", "contentBundleList", "databaseName"})
public class CourseStructure {

    private String name;

    private String databaseName;

    private List<ContentBundle> contentBundleList;

    /**
     * Getters for JAXB xml binding
     */
    @XmlElementWrapper(name = "contentBundles")
    @XmlElement(name = "contentBundle")
    public List<ContentBundle> getContentBundleList() {
        return contentBundleList;
    }

    /**
     * Adds a new ContentBundle
     */
    public void addContentBundle() {
        if (this.contentBundleList == null) {
            this.contentBundleList = new ArrayList<>();
        }
        this.contentBundleList.add((new ContentBundle()).getInitialContentBundle());
    }

    /**
     * deletes an element from the list by index
     *
     * @param index
     */
    public void deleteContentBundleByIndex(int index) {
        this.contentBundleList.remove(index);
    }

    /**
     * Add a new field in ith ContentBundle Table
     *
     * @param index
     */
    public void addFieldIntoTableByContentBundleIndex(int index) {
        ContentBundle contentBundle = this.contentBundleList.get(index);
        Table table = contentBundle.getTable();
        table.addNewField();
        contentBundle.setTable(table);
        this.contentBundleList.set(index, contentBundle);
    }

    /**
     * @param index
     * @param fieldNameIndex
     */
    public void deleteFieldNameFromTableByContentBundleIndex(int index,
                                                             int fieldNameIndex) {
        ContentBundle contentBundle = this.contentBundleList.get(index);
        Table table = contentBundle.getTable();
        table.deleteFieldName(fieldNameIndex);
        contentBundle.setTable(table);
        this.contentBundleList.set(index, contentBundle);
    }

    /**
     * <p>Resets the unselected contents</p>
     */
    public void reinitialize() {
        if (this.contentBundleList == null) {
            this.contentBundleList = new ArrayList<>();
            return;
        }

        List<ContentBundle> contentBundles = new ArrayList<>();
        for (ContentBundle contentBundle : this.contentBundleList) {
            if (contentBundle.getSelected() == 0) {
                contentBundle.setTable((new Table()).getInitialTable(0));
            } else if (contentBundle.getSelected() == 1) {
                contentBundle.setTextArea((new TextArea()).getInitialTextArea(0));
            }
            contentBundles.add(contentBundle);
        }
        this.contentBundleList = contentBundles;
    }
}
