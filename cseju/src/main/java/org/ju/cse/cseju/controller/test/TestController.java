package org.ju.cse.cseju.controller.test;

import org.ju.cse.cseju.message.RequestResponse;
import org.ju.cse.cseju.model.syllabus.organizer.CourseType;
import org.ju.cse.cseju.repository.basex.BaseXRepository;
import org.ju.cse.cseju.service.test.CourseTypeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/get")
    public String getSyllabus() {
        return baseXRepository.read("//syllabus");
    }

    @GetMapping("/addNewCourseType/{name}")
    public String testAddNewCourseType(
            @PathVariable("name") String name
    ) {
        CourseType courseType =
                new CourseType(name);

        courseTypeServices.addNewCourseType(
                SYLLABUS_NAME,
                courseType
        );
        System.err.println(baseXRepository.read("/"));
        return courseTypeServices.getCourseTypeByCourseTypeName(
                SYLLABUS_NAME,
                name
        ).toString();
    }

    @GetMapping("/deleteCourseType/{name}")
    public String testDeleteCourseTypeById(@PathVariable("name") String name) {
        courseTypeServices.deleteCourseTypeByCourseTypeName(
                SYLLABUS_NAME,
                name
        );
        System.err.println(baseXRepository.read("/"));
        return baseXRepository.read("/");
    }

    @GetMapping("/getAll/{syllabusName}")
    public List<String> testgetAllCourseTypeNamesBySyllabusName(
            @PathVariable("syllabusName") String syllabusName
    ) {
        return (List<String>) courseTypeServices.getAllCourseTypeNames(syllabusName);
    }

    @GetMapping("/getAllCt/{syllabusName}")
    public String getAllCourseTypeNames(
            @PathVariable("syllabusName") String syllabusName
    ) {
        String data = baseXRepository.getAllAsXMLString(
                "courseTypes",
                "syllabusName",
                syllabusName,
                "courseType"
        );
        System.err.println(data);
        return data;
    }

    /*@GetMapping("/getAllCT/{syllabusName")
    public RequestResponse getAllCourseTypeNames() {
        String data = baseXRepository.getAllAsXMLString(
                "courseTypes",
                "syllabusName",
                SYLLABUS_NAME,
                "courseType"
        );
        System.err.println(data);
        return new RequestResponse("done", null);
    }*/
}
