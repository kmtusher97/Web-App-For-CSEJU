package org.ju.cse.cseju.service.test;

import org.ju.cse.cseju.model.syllabus.organizer.CourseType;
import org.ju.cse.cseju.repository.basex.BaseXRepository;
import org.ju.cse.cseju.service.jaxb.JAXBServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author tshr
 */
@Service
public class CourseTypeServices {

    private static final String PARENT_NODE = "courseTypes";
    private static final String PARENT_NODE_ATTRIBUTE_NAME = "syllabusName";
    private static final String NODE_NAME = "courseType";
    private static final String NODE_ATTRIBUTE_NAME = "name";

    private static JAXBServices jaxbServices = new JAXBServices();

    @Autowired
    private BaseXRepository baseXRepository;


    /**
     * @param syllabusName
     * @return
     */
    public List<String> getAllCourseTypeNames(String syllabusName) {
        String result = baseXRepository.getAllAsData(
                PARENT_NODE,
                PARENT_NODE_ATTRIBUTE_NAME,
                syllabusName,
                "courseType/(@name)"
        );

        String[] courseTypeNames = result.split("\n");
        return Arrays.asList(courseTypeNames);
    }

    /**
     * @param syllabusName
     * @param courseType
     */
    public void addNewCourseType(String syllabusName,
                                 CourseType courseType) {
        baseXRepository.saveOrUpdate(
                PARENT_NODE,
                PARENT_NODE_ATTRIBUTE_NAME,
                syllabusName,
                jaxbServices.objectToXmlString(
                        courseType,
                        false
                ),
                NODE_NAME,
                NODE_ATTRIBUTE_NAME,
                courseType.getCourseTypeName()
        );

        /**initialize course structure**/
        baseXRepository.insert(
                NODE_NAME,
                NODE_ATTRIBUTE_NAME,
                courseType.getCourseTypeName(),
                "<courseStructure/>"
        );
    }

    /**
     * @param syllabusName
     * @param courseTypeName
     * @return CourseTypeBySyllabusNameAndId
     */
    public CourseType getCourseTypeByCourseTypeName(
            String syllabusName,
            String courseTypeName
    ) {
        return (CourseType) jaxbServices
                .xmlStringToObject(
                        baseXRepository.getByParentAndId(
                                PARENT_NODE,
                                PARENT_NODE_ATTRIBUTE_NAME,
                                syllabusName,
                                NODE_NAME,
                                NODE_ATTRIBUTE_NAME,
                                courseTypeName
                        ),
                        new CourseType()
                );
    }

    /**
     * @param syllabusName
     * @param courseTypeName
     */
    public void deleteCourseTypeByCourseTypeName(
            String syllabusName,
            String courseTypeName
    ) {
        baseXRepository.delete(
                PARENT_NODE,
                PARENT_NODE_ATTRIBUTE_NAME,
                syllabusName,
                NODE_NAME,
                NODE_ATTRIBUTE_NAME,
                courseTypeName
        );
    }

    /**
     * @param syllabusName
     * @return CourseTypes as XML String
     */
    public String getCourseTypes(String syllabusName) {
        return baseXRepository.read(
                "//" + PARENT_NODE + "[@" + PARENT_NODE_ATTRIBUTE_NAME + "=\'" +
                        syllabusName + "\']"
        );
    }
}
