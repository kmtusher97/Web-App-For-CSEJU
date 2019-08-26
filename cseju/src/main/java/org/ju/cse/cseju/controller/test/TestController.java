package org.ju.cse.cseju.controller.test;

import org.ju.cse.cseju.model.syllabus.organizer.CourseType;
import org.ju.cse.cseju.repository.basex.BaseXRepository;
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
@RequestMapping("/test")
public class TestController {

    private static final String SYLLABUS_NAME = "undergrad_2018-2022";

    @Autowired
    private CourseTypeServices courseTypeServices;

    @Autowired
    private BaseXRepository baseXRepository;

    @GetMapping("/addNewCourseType/{id}/{name}")
    public String testAddNewCourseType(
            @PathVariable("id") Integer id,
            @PathVariable("name") String name
    ) {
        CourseType courseType =
                new CourseType(id, name);

        courseTypeServices.addNewCourseType(
                SYLLABUS_NAME,
                courseType
        );
        System.err.println(baseXRepository.read("/"));
        return courseTypeServices.getCourseTypeBySyllabusNameAndId(
                SYLLABUS_NAME,
                id
        ).toString();
    }

    @GetMapping("/deleteCourseType/{id}")
    public String testDeleteCourseTypeById(@PathVariable("id") Integer id) {
        courseTypeServices.deleteCourseTypeBySyllabusNameAndId(
                SYLLABUS_NAME,
                id
        );
        System.err.println(baseXRepository.read("/"));
        return baseXRepository.read("/");
    }
}
