package org.ju.cse.cseju.service.test;

import org.ju.cse.cseju.model.syllabus.CourseStructure;
import org.ju.cse.cseju.model.syllabus.content.ContentBundle;
import org.ju.cse.cseju.repository.basex.BaseXRepository;
import org.ju.cse.cseju.service.jaxb.JAXBServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tshr
 */
@Service
public class CourseStructureServices1 {
    private static final String PARENT0_NODE = "courseTypes";
    private static final String PARENT0_NODE_ATTRIBUTE_NAME = "syllabusName";
    private static final String PARENT1_NODE = "courseType";
    private static final String PARENT1_NODE_ATTRIBUTE_NAME = "name";
    private static final String NODE_NAME = "courseStructure";

    private static JAXBServices jaxbServices = new JAXBServices();

    @Autowired
    private BaseXRepository baseXRepository;

    /**
     * @param syllabusName
     * @param courseType
     * @return CourseStructure
     */
    public CourseStructure getCourseStructureByCourseType(
            String syllabusName,
            String courseType
    ) {
        String result = baseXRepository.read(
                "//" + PARENT0_NODE + "[@" + PARENT0_NODE_ATTRIBUTE_NAME + "=\"" +
                        syllabusName + "\"]//" + PARENT1_NODE + "[@" + PARENT1_NODE_ATTRIBUTE_NAME + "=\"" +
                        courseType + "\"]/" + NODE_NAME
        );

        return (CourseStructure) jaxbServices.xmlStringToObject(
                result,
                new CourseStructure()
        );
    }

    /**
     * @param syllabusName
     * @param courseType
     */
    public void addContentBundle(
            String syllabusName,
            String courseType
    ) {
        ContentBundle contentBundle = new ContentBundle();
        contentBundle = contentBundle.getInitialContentBundle();

        Integer contentBundleId = 0;
        if (getCountOfContentBundle(syllabusName, courseType) != 0) {
            contentBundleId = getNextContentBundleId(syllabusName, courseType) + 1;
        }

        contentBundle.setContentBundleId(
                contentBundleId
        );

        baseXRepository.write(
                "insert node " +
                        jaxbServices.objectToXmlString(contentBundle, false) +
                        " into " +
                        "//courseTypes[@syllabusName=\"" + syllabusName + "\"]//courseType[@name=\"" +
                        courseType + "\"]//courseStructure"
        );
    }

    /**
     * @param syllabusName
     * @param courseTypeName
     * @return
     */
    private Integer getNextContentBundleId(
            String syllabusName,
            String courseTypeName
    ) {
        String result = baseXRepository.read(
                "max(//courseTypes[@syllabusName=\"" + syllabusName +
                        "\"]//courseType[@name=\"" + courseTypeName + "\"]//contentBundle/[@id])"
        );
        if (result == "") return 0;
        return Integer.parseInt(result);
    }

    /**
     * @param syllabusName
     * @param courseType
     * @return CountOfContentBundle
     */
    private Integer getCountOfContentBundle(
            String syllabusName,
            String courseType
    ) {
        return Integer.parseInt(
                baseXRepository.getCountOfElement(
                        "count(//courseTypes[@syllabusName=\"" + syllabusName + "\"]//courseType[@name=\"" +
                                courseType + "\"]//contentBundle)"
                )
        );
    }

    /**
     * @param syllabusName
     * @param courseTypeName
     * @return CourseStructure as XML String
     */
    public String getCourseStructure(
            String syllabusName,
            String courseTypeName
    ) {
        return baseXRepository.read(
                "//" + PARENT0_NODE + "[@" + PARENT0_NODE_ATTRIBUTE_NAME + "=\"" +
                        syllabusName + "\"]//" + PARENT1_NODE + "[@" + PARENT1_NODE_ATTRIBUTE_NAME + "=\"" +
                        courseTypeName + "\"]/" + NODE_NAME
        );
    }
}
