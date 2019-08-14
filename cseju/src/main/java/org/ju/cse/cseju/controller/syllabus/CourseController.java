package org.ju.cse.cseju.controller.syllabus;

import org.ju.cse.cseju.model.syllabus.Course;
import org.ju.cse.cseju.model.syllabus.content.Content;
import org.ju.cse.cseju.service.syllabus.CourseServices;
import org.ju.cse.cseju.service.syllabus.CourseStructureServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Kamrul Hasan
 */
@Controller
@RequestMapping(path = "/course")
public class CourseController {

    private static final String SYLLABUS_VIEW_INPUT = "syllabus/input/";

    private CourseServices courseServices = new CourseServices();

    private CourseStructureServices courseStructureServices = new CourseStructureServices();

    /**
     * <h3>url: /course/inputForm/preview/{courseType}</h3>
     *
     * @param databaseName
     * @return ModelAndView
     */
    @GetMapping("/inputForm/preview/{databaseName}")
    public ModelAndView getCourseInputFormPreview(
            @PathVariable("databaseName") String databaseName
    ) {

        ModelAndView modelAndViewCourseInputForm = new ModelAndView(
                SYLLABUS_VIEW_INPUT + "courseInputForm"
        );

        Course course = courseServices.getCourseInputForm(databaseName);
        List<Content> contentList = courseStructureServices.getContentListForPreview(databaseName);

        modelAndViewCourseInputForm.addObject("course", course);
        modelAndViewCourseInputForm.addObject("contentList", contentList);

        return modelAndViewCourseInputForm;
    }

    /**
     * <h3>url: /course/inputForm/{databaseName}/{courseCode}</h3>
     *
     * @param databaseName
     * @return
     */
    @GetMapping("/inputForm/{databaseName}/{courseCode}")
    public ModelAndView getCourseInputForm(
            @PathVariable("databaseName") String databaseName,
            @PathVariable("courseCode") String courseCode
    ) {

        /*ModelAndView modelAndViewCourseInputForm = new ModelAndView(
                SYLLABUS_VIEW_INPUT + "courseInputForm"
        );

        Course course = courseServices.getCourseInputForm(databaseName, courseCode);
        modelAndViewCourseInputForm.addObject("course", course);
        modelAndViewCourseInputForm.addObject("databaseName", databaseName);

        return modelAndViewCourseInputForm;*/
        return null;
    }


    @GetMapping("/{databaseName}/add_input_row/{contentId}")
    public ModelAndView addInputRowInTable(
            @PathVariable("databaseName") String databaseName,
            @PathVariable("contentId") Integer contentId
    ) {
        courseServices.addRowInTable(databaseName, (int) contentId);

        return new ModelAndView(
                "redirect:/course/inputForm/" + databaseName
        );
    }
}
