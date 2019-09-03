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
@RequestMapping(path = "/courseStructure/Data/")
public class CourseStructureRestController {

    @Autowired
    private CourseStructureServices courseStructureServices;


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
