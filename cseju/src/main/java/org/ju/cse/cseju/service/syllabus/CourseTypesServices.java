package org.ju.cse.cseju.service.syllabus;

import org.ju.cse.cseju.model.syllabus.organizer.CourseTypes;
import org.ju.cse.cseju.repository.syllabus.CourseTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamrul Hasan
 */
@Service
public class CourseTypesServices {

    private CourseTypeRepository courseTypeRepository = new CourseTypeRepository();

    private CourseStructureServices courseStructureServices = new CourseStructureServices();


    /**
     * @param syllabusName
     * @return CourseTypes
     */
    public CourseTypes getCourseTypes(String syllabusName) {
        return courseTypeRepository.getCourseTypes(
                syllabusName /**databaseName = SyllabusDraftController Name**/
        );
    }

    /**
     * @param databaseName
     * @param courseType
     */
    public void addCourseType(String databaseName, String courseType) {
        CourseTypes courseTypes = courseTypeRepository.getCourseTypes(
                databaseName /**databaseName = SyllabusDraftController Name**/
        );

        List<String> typeList = courseTypes.getCourseTypeList();
        if (typeList == null) {
            typeList = new ArrayList<>();
        }
        typeList.add(courseType);
        courseTypes.setCourseTypeList(typeList);

        courseStructureServices.createNewCourseStructure(databaseName, courseType);
        courseTypeRepository.saveOrUpdate(databaseName, courseTypes);
    }

    /**
     * @param syllabusName
     * @param courseType
     */
    public void deleteCourseType(String syllabusName, String courseType) {
        CourseTypes courseTypes = courseTypeRepository.getCourseTypes(
                syllabusName /**databaseName = SyllabusDraftController Name**/
        );

        List<String> typeList = courseTypes.getCourseTypeList();
        typeList.remove(courseType);
        courseTypes.setCourseTypeList(typeList);

        courseStructureServices.deleteCourseStructure(
                syllabusName, /**databaseName = syllabusName**/
                courseType
        );
        /**
         * Future addition: delete all the courses of this type from the draft syllabus repo
         */
        courseTypeRepository.saveOrUpdate(
                syllabusName, /**databaseName = syllabusName**/
                courseTypes
        );
    }

    /**
     * @param syllabusName
     */
    public void createSyllabusCourseTypes(String syllabusName) {
        courseTypeRepository.create(syllabusName);
    }
}
