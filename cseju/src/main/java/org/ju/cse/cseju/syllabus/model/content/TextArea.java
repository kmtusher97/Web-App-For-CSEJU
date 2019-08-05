package org.ju.cse.cseju.syllabus.model.content;

import lombok.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author Kamrul Hasan
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "textArea")
@XmlType(propOrder = {"title", "textBody"})
public class TextArea extends Content implements Serializable {

    private Integer textAreaId;

    private String title;

    private String textBody;

    public TextArea(String title, String textBody) {
        this.title = title;
        this.textBody = textBody;
    }

    @XmlAttribute(name = "textAreaId")
    public Integer getTextAreaId() {
        return textAreaId;
    }

    public TextArea getInitialTextArea(Integer textAreaId) {
        return new TextArea(textAreaId, "Untitled TextArea", "Empty Text Body");
    }
}
