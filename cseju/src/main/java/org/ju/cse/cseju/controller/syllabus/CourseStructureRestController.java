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
        courseStructureServices.addContentBundleByCourseType(
                syllabusName,
                courseTypeName
        );

        return "added";
    }
}
