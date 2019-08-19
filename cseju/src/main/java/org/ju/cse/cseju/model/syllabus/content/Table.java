package org.ju.cse.cseju.model.syllabus.content;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
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
@XmlRootElement(name = "table")
@XmlType(propOrder = {"title", "fields", "rows"})
public class Table extends Content implements Serializable {

    private Integer tableId;

    private String title;

    private List<String> fields;

    private List<TableRow> rows;

    public Table(String title, List<String> fields) {
        this.title = title;
        this.fields = fields;
        this.rows = new ArrayList<>();
    }

    /**
     * Getters for xml mapping
     */
    @XmlAttribute(name = "contentId")
    public Integer getTableId() {
        return tableId;
    }

    @XmlElementWrapper(name = "fields")
    @XmlElement(name = "field")
    public List<String> getFields() {
        return fields;
    }

    @XmlElementWrapper(name = "rows")
    @XmlElement(name = "row")
    public List<TableRow> getRows() {
        return rows;
    }


    public void handleNullPointerExceptionForListFields() {
        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }
        if (this.rows == null) {
            this.rows = new ArrayList<>();
        }
    }

    /**
     * Add a new field
     */
    public void addNewField() {
        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }
        this.fields.add("Field" + this.fields.size());
    }

    /**
     * @param fieldNo
     */
    public void deleteFieldName(int fieldNo) {
        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }
        if (fieldNo >= this.fields.size()) {
            return;
        }
        this.fields.remove(fieldNo);
    }

    /**
     * @param rowId
     */
    public void addRow(int rowId) {
        handleNullPointerExceptionForListFields();

        TableRow tableRow = new TableRow();
        tableRow.setTableRowId(rowId);
        for (int i = 0; i < this.fields.size(); i++) {
            tableRow.addCell();
        }
        this.rows.add(tableRow);
    }

    /**
     * @param tableId
     * @return Table
     */
    public Table getInitialTable(Integer tableId) {
        Table table = new Table();
        table.setTableId(tableId);
        table.setTitle("Untitled Table");
        table.addNewField();

        return table;
    }

    /**
     * @param rowIndex
     */
    public void deleteRowByRowIndex(int rowIndex) {
        handleNullPointerExceptionForListFields();

        if (rowIndex >= this.rows.size()) return;

        this.rows.remove(rowIndex);
    }
}
