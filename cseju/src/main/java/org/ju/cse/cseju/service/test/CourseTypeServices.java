package org.ju.cse.cseju.service.test;

import org.ju.cse.cseju.model.syllabus.organizer.CourseType;
import org.ju.cse.cseju.repository.basex.BaseXRepository;
import org.ju.cse.cseju.service.jaxb.JAXBServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tshr
 */
@Service
public class CourseTypeServices {

    private static final String PARENT_NODE = "courseTypes";
    private static final String PARENT_NODE_ATTRIBUTE_NAME = "syllabusName";
    private static final String NODE_NAME = "courseType";
    private static final String NODE_ATTRIBUTE_NAME = "id";

    private static JAXBServices jaxbServices = new JAXBServices();

    @Autowired
    private BaseXRepository baseXRepository;


    /*public  getAllCourseTypes() {

    }*/

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
                Integer.toString(courseType.getId())
        );
    }

    /**
     * @param syllabusName
     * @param id
     * @return CourseTypeBySyllabusNameAndId
     */
    public CourseType getCourseTypeBySyllabusNameAndId(String syllabusName,
                                                       Integer id) {
        return (CourseType) jaxbServices
                .xmlStringToObject(
                        baseXRepository.getByParentAnId(
                                PARENT_NODE,
                                PARENT_NODE_ATTRIBUTE_NAME,
                                syllabusName,
                                NODE_NAME,
                                NODE_ATTRIBUTE_NAME,
                                Integer.toString(id)
                        ),
                        new CourseType()
                );
    }

    /**
     * @param syllabusName
     * @param id
     */
    public void deleteCourseTypeBySyllabusNameAndId(String syllabusName,
                                                    Integer id) {
        baseXRepository.delete(
                PARENT_NODE,
                PARENT_NODE_ATTRIBUTE_NAME,
                syllabusName,
                NODE_NAME,
                NODE_ATTRIBUTE_NAME,
                Integer.toString(id)
        );
    }
}
