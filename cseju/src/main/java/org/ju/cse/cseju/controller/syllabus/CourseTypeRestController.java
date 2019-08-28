package org.ju.cse.cseju.controller.syllabus;

import org.ju.cse.cseju.service.test.CourseTypeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tshr
 */
@RestController
@RequestMapping(path = "/courseType/Data")
public class CourseTypeRestController {

    @Autowired
    private CourseTypeServices courseTypeServices;

    /**
     * <h3>url: /courseType/Data/{syllabusName}</h3>
     *
     * @param syllabusName
     * @return CourseTypes by SyllabusName
     */
    @GetMapping("/{syllabusName}")
    public String getCourseTypes(
            @PathVariable("syllabusName") String syllabusName
    ) {
        return courseTypeServices.getCourseTypes(syllabusName);
    }


}
