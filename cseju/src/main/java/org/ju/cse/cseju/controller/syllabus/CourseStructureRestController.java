package org.ju.cse.cseju.controller.syllabus;

import org.ju.cse.cseju.message.RequestResponse;
import org.ju.cse.cseju.model.syllabus.CourseStructure;
import org.ju.cse.cseju.service.syllabus.CourseStructureServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tshr
 */
@RestController
@RequestMapping(path = "/courseStructure/Data")
public class CourseStructureRestController {

    @Autowired
    private CourseStructureServices courseStructureServices;


    /**
     * <h3>url: /courseStructure/Data/{syllabusName}/{courseTypeName} </h3>
     *
     * @param syllabusName
     * @param courseTypeName
     * @return courseStructure as Xml String
     */
    @GetMapping("/{syllabusName}/{courseTypeName}")
    public String getCourseStructure(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("courseTypeName") String courseTypeName
    ) {
        return courseStructureServices.getCourseStructure(
                syllabusName,
                courseTypeName
        );
    }


    /**
     * <h3>url: /courseStructure/Data/{syllabusName}/{courseTypeName}/add </h3>
     * Adds a new contentBundle
     *
     * @param syllabusName
     * @param courseTypeName
     * @return String
     */
    @GetMapping("/{syllabusName}/{courseTypeName}/add")
    public String addContentBundle(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("courseTypeName") String courseTypeName
    ) {
        courseStructureServices.addContentBundle(
                syllabusName,
                courseTypeName
        );

        return "added";
    }

    /**
     * <h3>url: /courseStructure/Data/{syllabusName}/{courseTypeName}/deleteContentBundle/{id} </h3>
     * Deletes a selected contentBundle
     *
     * @param syllabusName
     * @param courseTypeName
     * @param id
     * @return String
     */
    @GetMapping("/{syllabusName}/{courseTypeName}/deleteContentBundle/{id}")
    public String deleteContentBundle(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("courseTypeName") String courseTypeName,
            @PathVariable("id") Integer id
    ) {
        courseStructureServices.deleteContentBundle(
                syllabusName,
                courseTypeName,
                id
        );

        return "deleted";
    }


    /**
     * <h3>url: /courseStructure/Data/{syllabusName}/{courseTypeName}/addField/{id} </h3>
     * Adds new field in Table
     *
     * @param syllabusName
     * @param courseTypeName
     * @param id
     * @return String
     */
    @GetMapping("/{syllabusName}/{courseTypeName}/addField/{id}")
    public String addFieldInTableContent(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("courseTypeName") String courseTypeName,
            @PathVariable("id") Integer id
    ) {
        courseStructureServices.addFieldInTableContent(
                syllabusName,
                courseTypeName,
                id

        );
        return "fieldAdded";
    }

    /**
     * <h3>url: /courseStructure/Data/{syllabusName}/{courseTypeName}/deleteField/{id}/{fieldId} </h3>
     * Deletes a field from tableContent
     *
     * @param syllabusName
     * @param courseTypeName
     * @param contentBundleId
     * @param fieldId
     * @return String
     */
    @GetMapping("/{syllabusName}/{courseTypeName}/deleteField/{id}/{fieldId}")
    public String deleteFieldNameFromTableContent(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("courseTypeName") String courseTypeName,
            @PathVariable("id") Integer contentBundleId,
            @PathVariable("fieldId") Integer fieldId
    ) {
        courseStructureServices.deleteFieldNameFromTableContent(
                syllabusName,
                courseTypeName,
                contentBundleId,
                fieldId
        );

        return "fieldDeleted";
    }

    /**
     * <h3>url: /courseStructure/Data//{syllabusName}/{courseTypeName}/autosave </h3>
     * saves the changes of courseStructure
     *
     * @param syllabusName
     * @param courseTypeName
     * @param courseStructure
     * @return String
     */
    @PostMapping("/{syllabusName}/{courseTypeName}/autosave")
    public RequestResponse autoSaveChanges(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("courseTypeName") String courseTypeName,
            @RequestBody CourseStructure courseStructure
    ) {
        courseStructureServices.saveOrUpdateCourseStructure(
                syllabusName,
                courseTypeName,
                courseStructure
        );

        return new RequestResponse("saved", null);
    }
}
