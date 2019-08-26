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
     * <h3>url: /courseStructure/design/{syllabusName}/{courseType}</h3>
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
                courseStructureServices.getCourseStructureByCourseType(
                        syllabusName,
                        courseType
                );

        modelAndViewDesignCourseInputForm.addObject(
                "courseStructure",
                courseStructure
        );
        modelAndViewDesignCourseInputForm.addObject(
                "syllabusName",
                syllabusName
        );
        modelAndViewDesignCourseInputForm.addObject(
                "courseType",
                courseType
        );

        return modelAndViewDesignCourseInputForm;
    }


    /**
     * <h3>url: /courseStructure/addContentBundle/{syllabusName}/{courseType}</h3>
     *
     * @param syllabusName
     * @param courseType
     * @return redirects to courseStructure/design/{syllabusName}/{courseType}
     */
    @GetMapping("/addContentBundle/{syllabusName}/{courseType}")
    public ModelAndView addContentBundle(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("courseType") String courseType
    ) {
        courseStructureServices.addContentBundleByCourseType(
                syllabusName,
                courseType
        );

        return new ModelAndView(
                "redirect:/courseStructure/design/" +
                        syllabusName + "/" + courseType
        );
    }
}
