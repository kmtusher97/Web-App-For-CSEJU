package org.ju.cse.cseju.controller.test;

import org.ju.cse.cseju.model.syllabus.CourseStructure;
import org.ju.cse.cseju.service.test.CourseStructureServices1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author tshr
 */
@Controller
@RequestMapping(path = "/courseStructure")
public class CourseStructureController1 {

    private static final String SYLLABUS_VIEW_INPUT = "syllabus/input/";

    @Autowired
    private CourseStructureServices1 courseStructureServices;

    /**
     * <h3>url: /course_structure/design/{syllabusName}/{courseType}</h3>
     *
     * @param syllabusName
     * @param courseType
     * @return ModelAndView CourseInputFromDesignPage
     */
    @GetMapping("/design/{syllabusName}/{courseType}")
    public ModelAndView getCourseInputFromDesignPage(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("courseType") String courseType
    ) {
        ModelAndView modelAndViewDesignCourseInputForm = new ModelAndView(
                SYLLABUS_VIEW_INPUT + "courseInputFormDesignPage"
        );

        CourseStructure courseStructure =
                courseStructureServices.getCourseStructure(
                        syllabusName,
                        courseType
                );

        modelAndViewDesignCourseInputForm.addObject(
                "courseStructure",
                courseStructure
        );

        return modelAndViewDesignCourseInputForm;
    }
}
