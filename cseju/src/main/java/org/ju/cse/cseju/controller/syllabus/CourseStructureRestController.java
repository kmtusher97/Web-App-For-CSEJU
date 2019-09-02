package org.ju.cse.cseju.controller.syllabus;

import org.ju.cse.cseju.service.test.CourseStructureServices1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tshr
 */
@RestController
@RequestMapping(path = "/courseStructure/Data/")
public class CourseStructureRestController {

    @Autowired
    private CourseStructureServices1 courseStructureServices;


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


    @GetMapping("/{syllabusName}/{courseTypeName}/deleteContentBundle/{contentBundleId}")
    public String deleteContentBundle(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("courseTypeName") String courseTypeName,
            @PathVariable("contentBundleId") Integer id
    ) {
        courseStructureServices.deleteContentBundle(
                syllabusName,
                courseTypeName,
                id
        );

        return "deleted";
    }


    @GetMapping("/{syllabusName}/{courseTypeName}/addField/{contentBundleId}")
    public String addFieldInTableContent(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("courseTypeName") String courseTypeName,
            @PathVariable("contentBundleId") Integer id
    ) {
        courseStructureServices.addFieldInTableContent(
                syllabusName,
                courseTypeName,
                id

        );
        return "fieldAdded";
    }


    @GetMapping("/{syllabusName}/{courseTypeName}/deleteField/{contentBundleId}/{fieldId}")
    public String deleteFieldNameFromTableContent(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("courseTypeName") String courseTypeName,
            @PathVariable("contentBundleId") Integer contentBundleId,
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
}
