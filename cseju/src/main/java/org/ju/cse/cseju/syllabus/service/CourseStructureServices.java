package org.ju.cse.cseju.syllabus.service;

import org.ju.cse.cseju.syllabus.model.CourseStructure;
import org.ju.cse.cseju.syllabus.repository.CourseStructureRepository;
import org.springframework.stereotype.Service;

/**
 * @author Kamrul Hasan
 */
@Service("CourseStructureServices")
public class CourseStructureServices {

    private CourseStructureRepository courseStructureRepository = new CourseStructureRepository();

    /**
     * @param databaseName
     * @param courseStructure
     */
    public void saveOrUpdate(String databaseName, CourseStructure courseStructure) {
        courseStructure.reinitialize();
        courseStructureRepository.saveOrUpdate(databaseName, courseStructure);
    }

    /**
     * @param databaseName
     * @return CourseStructure
     */
    public CourseStructure getCourseStructure(String databaseName) {
        return courseStructureRepository.getCourseStructure(databaseName);
    }

    /**
     * @param databaseName
     */
    public void addContentBundle(String databaseName) {
        CourseStructure courseStructure = getCourseStructure(databaseName);
        courseStructure.addContentBundle();
        saveOrUpdate(databaseName, courseStructure);
    }

    /**
     * delete a ContentBundle by row index
     *
     * @param databaseName
     * @param contentBundleId
     */
    public void deleteContentBundleByContentBundleId(String databaseName,
                                                     Integer contentBundleId) {
        CourseStructure courseStructure = getCourseStructure(databaseName);
        courseStructure.deleteContentBundleByIndex((int) contentBundleId);
        saveOrUpdate(databaseName, courseStructure);
    }

    /**
     * @param databaseName
     * @param contentBundleId
     */
    public void addFieldNameIntoTableByContentBundleId(String databaseName,
                                                       Integer contentBundleId) {
        CourseStructure courseStructure = getCourseStructure(databaseName);
        courseStructure.addFieldIntoTableByContentBundleIndex((int) contentBundleId);
        saveOrUpdate(databaseName, courseStructure);
    }

    /**
     * @param databaseName
     * @param contentBundleId
     * @param fieldNameId
     */
    public void deleteFieldNameFromTableByContentBundleId(String databaseName,
                                                          Integer contentBundleId,
                                                          Integer fieldNameId) {
        CourseStructure courseStructure = getCourseStructure(databaseName);
        courseStructure.deleteFieldNameFromTableByContentBundleIndex(contentBundleId, fieldNameId);
        saveOrUpdate(databaseName, courseStructure);
    }
}
