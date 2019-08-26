package org.ju.cse.cseju.service.test;

import org.ju.cse.cseju.model.syllabus.CourseStructure;
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

    public CourseStructure getCourseStructure(
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
}
