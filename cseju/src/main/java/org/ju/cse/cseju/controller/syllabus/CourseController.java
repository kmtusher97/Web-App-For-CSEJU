package org.ju.cse.cseju.controller.syllabus;

import org.ju.cse.cseju.model.syllabus.Course;
import org.ju.cse.cseju.model.syllabus.content.Content;
import org.ju.cse.cseju.model.syllabus.content.Contents;
import org.ju.cse.cseju.service.syllabus.CourseServices;
import org.ju.cse.cseju.service.syllabus.CourseStructureServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

        /*ModelAndView modelAndViewCourseInputForm = new ModelAndView(
                SYLLABUS_VIEW_INPUT + "courseInputForm"
        );

        Course course = courseServices.getCourseInputForm(databaseName);
        List<Content> contentList = courseStructureServices.getContentListForPreview(databaseName);

        modelAndViewCourseInputForm.addObject("course", course);
        modelAndViewCourseInputForm.addObject("contentList", contentList);

        return modelAndViewCourseInputForm;*/
        return null;
    }

    /**
     * <h3>url: /course/inputForm/{syllabusName}/{yearId}/{semesterId}/{courseCode}</h3>
     *
     * @param syllabusName
     * @param yearId
     * @param semesterId
     * @param courseCode
     * @return
     */
    @GetMapping("/inputForm/{syllabusName}/{yearId}/{semesterId}/{courseCode}")
    public ModelAndView getCourseInputForm(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("yearId") Integer yearId,
            @PathVariable("semesterId") Integer semesterId,
            @PathVariable("courseCode") String courseCode
    ) {
        ModelAndView modelAndViewCourseInputForm = new ModelAndView(
                SYLLABUS_VIEW_INPUT + "courseInputForm"
        );

        modelAndViewCourseInputForm.addObject(
                "syllabusName",
                syllabusName
        );

        Course course = courseServices
                .getCourseBySyllabusNameAndYearIdAndSemesterIdAndCourseCode(
                        syllabusName,
                        yearId,
                        semesterId,
                        courseCode
                );

        modelAndViewCourseInputForm.addObject(
                "course",
                course
        );

        Contents contents = new Contents(
                course.getContentList()
        );

        modelAndViewCourseInputForm.addObject(
                "contents",
                contents
        );

        return modelAndViewCourseInputForm;
    }


    /**
     * <h3>url: /course/{syllabusName}/{yearId}/{semesterId}/{courseCode}/addInputRow/{contentId}</h3>
     *
     * @param syllabusName
     * @param yearId
     * @param semesterId
     * @param courseCode
     * @param contentId
     * @return redirect:/course/inputForm/{syllabusName}/{yearId}/{semesterId}/{courseCode}
     */
    @GetMapping("/{syllabusName}/{yearId}/{semesterId}/{courseCode}/addInputRow/{contentId}")
    public ModelAndView addInputRowInTable(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("yearId") Integer yearId,
            @PathVariable("semesterId") Integer semesterId,
            @PathVariable("courseCode") String courseCode,
            @PathVariable("contentId") Integer contentId
    ) {
        courseServices.addRowInTableByTableId(
                syllabusName,
                yearId,
                semesterId,
                courseCode,
                contentId
        );

        return new ModelAndView(
                "redirect:/course/inputForm/" +
                        syllabusName + "/" +
                        yearId + "/" +
                        semesterId + "/" +
                        courseCode
        );
    }


    /**
     * <h3>url: /course/{syllabusName}/{yearId}/{semesterId}/{courseCode}/deleteInputRow/{contentId}/{rowIndex}</h3>
     *
     * @param syllabusName
     * @param yearId
     * @param semesterId
     * @param courseCode
     * @param contentId
     * @param rowIndex
     * @return redirect:/course/inputForm/{syllabusName}/{yearId}/{semesterId}/{courseCode}
     */
    @GetMapping("/{syllabusName}/{yearId}/{semesterId}/{courseCode}/deleteInputRow/{contentId}/{rowIndex}")
    public ModelAndView deleteInputRowFromTableByRowIndex(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("yearId") Integer yearId,
            @PathVariable("semesterId") Integer semesterId,
            @PathVariable("courseCode") String courseCode,
            @PathVariable("contentId") Integer contentId,
            @PathVariable("rowIndex") int rowIndex
    ) {
        courseServices.deleteInputRowFromTableByRowIndex(
                syllabusName,
                yearId,
                semesterId,
                courseCode,
                contentId,
                rowIndex
        );

        return new ModelAndView(
                "redirect:/course/inputForm/" +
                        syllabusName + "/" +
                        yearId + "/" +
                        semesterId + "/" +
                        courseCode
        );
    }
}
