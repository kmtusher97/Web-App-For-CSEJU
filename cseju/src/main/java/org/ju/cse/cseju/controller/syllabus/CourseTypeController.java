package org.ju.cse.cseju.controller.syllabus;

import org.ju.cse.cseju.model.syllabus.clientIO.TextInput;
import org.ju.cse.cseju.model.syllabus.organizer.CourseType;
import org.ju.cse.cseju.service.syllabus.CourseTypeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author tshr
 */
@Controller
@RequestMapping(path = "/courseType")
public class CourseTypeController {

    private static final String SYLLABUS_VIEW_INPUT = "syllabus/input/";

    @Autowired
    private CourseTypeServices courseTypeServices;


    @GetMapping("/{syllabusName}")
    public ModelAndView getCourseTypes(
            @PathVariable("syllabusName") String syllabusName
    ) {
        ModelAndView modelAndViewCourseTypePage =
                new ModelAndView(
                        SYLLABUS_VIEW_INPUT + "courseTypePage"
                );

        modelAndViewCourseTypePage.addObject(syllabusName);
        modelAndViewCourseTypePage.addObject(
                "newCourseType",
                new TextInput()
        );
        return modelAndViewCourseTypePage;
    }


    /**
     * <h3>url: /courseType/{syllabusName}/saveNewType</h3>
     *
     * @param syllabusName
     * @param newCourseType
     * @return ModelAndView redirect:/courseType/{syllabusName}
     */
    @PostMapping("/{syllabusName}/saveNewType")
    public ModelAndView saveNewCouresType(
            @ModelAttribute("newCourseType") TextInput newCourseType,
            @PathVariable("syllabusName") String syllabusName
    ) {

        courseTypeServices.addNewCourseType(
                syllabusName,
                new CourseType(newCourseType.getText())
        );

        return new ModelAndView("redirect:/courseType/" + syllabusName);
    }


    /**
     * <h3>url: /courseType/{syllabusName}/deleteType/{courseTypeName}</h3>
     *
     * @param syllabusName
     * @param courseTypeName
     * @return ModelAndView redirect:/courseType/{syllabusName}
     */
    @GetMapping("/{syllabusName}/deleteType/{courseTypeName}")
    public ModelAndView deleteCourseType(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("courseTypeName") String courseTypeName
    ) {
        courseTypeServices.deleteCourseTypeByCourseTypeName(
                syllabusName,
                courseTypeName
        );

        return new ModelAndView("redirect:/courseType/" + syllabusName);
    }
}
