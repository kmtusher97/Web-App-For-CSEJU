package org.ju.cse.cseju.syllabus.controller;

import org.ju.cse.cseju.syllabus.model.clientIO.TextInput;
import org.ju.cse.cseju.syllabus.model.organizer.CourseTypes;
import org.ju.cse.cseju.syllabus.service.CourseTypesServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * @author Kamrul Hasan
 */
@Controller
@RequestMapping(path = "/courseType")
public class CourseTypeController {
    private static final String SYLLABUS_VIEW_INPUT = "syllabus/input/";

    private CourseTypesServices courseTypesServices = new CourseTypesServices();


    /**
     * <h3>url: /courseType/{syllabusName}</h3>
     *
     * @param syllabusName
     * @return
     */
    @GetMapping("/{syllabusName}")
    public ModelAndView getCourseTypes(
            @PathVariable("syllabusName") String syllabusName
    ) {
        ModelAndView modelAndViewCourseTypeListPage = new ModelAndView(
                SYLLABUS_VIEW_INPUT + "courseTypePage"
        );

        TextInput textInput = new TextInput();
        CourseTypes courseTypes = courseTypesServices.getCourseTypes(syllabusName);

        if (courseTypes.getCourseTypeList() == null) {
            courseTypes.setCourseTypeList(new ArrayList<>());
        }

        modelAndViewCourseTypeListPage.addObject("courseTypes", courseTypes);
        modelAndViewCourseTypeListPage.addObject("newCourseType", textInput);

        return modelAndViewCourseTypeListPage;
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
        courseTypesServices.addCourseType(syllabusName, newCourseType.getText());

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
        courseTypesServices.deleteCourseType(syllabusName, courseTypeName);

        return new ModelAndView("redirect:/courseType/" + syllabusName);
    }
}
