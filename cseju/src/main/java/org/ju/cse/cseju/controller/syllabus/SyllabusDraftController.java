package org.ju.cse.cseju.controller.syllabus;

import org.ju.cse.cseju.model.syllabus.Course;
import org.ju.cse.cseju.model.syllabus.SyllabusDraft;
import org.ju.cse.cseju.service.syllabus.CourseStructureServices;
import org.ju.cse.cseju.service.syllabus.CourseTypesServices;
import org.ju.cse.cseju.service.syllabus.SyllabusDraftServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Kamrul Hasan
 */
@Controller
@RequestMapping(path = "/syllabusDraft")
public class SyllabusDraftController {

    private static final String SYLLABUS_VIEW_INPUT = "syllabus/input/";

    private SyllabusDraftServices syllabusDraftServices = new SyllabusDraftServices();

    private CourseStructureServices courseStructureServices = new CourseStructureServices();

    private CourseTypesServices courseTypesServices = new CourseTypesServices();


    /**
     * <h3>url: /syllabusDraft/edit/{syllabusName}</h3>
     *
     * @param syllabusName
     * @return ModelAndView EditSyllabusPage
     */
    @GetMapping("/edit/{syllabusName}")
    public ModelAndView getEditSyllabusPage(
            @PathVariable("syllabusName") String syllabusName
    ) {
        ModelAndView modelAndViewEditSyllabusPage = new ModelAndView(
                SYLLABUS_VIEW_INPUT + "syllabusDraftEditPage"
        );

        SyllabusDraft syllabusDraft = syllabusDraftServices.getSyllabusDraft(syllabusName);

        modelAndViewEditSyllabusPage.addObject("syllabus", syllabusDraft);
        modelAndViewEditSyllabusPage.addObject("newCourse", new Course());
        modelAndViewEditSyllabusPage.addObject(
                "courseTypes",
                courseTypesServices.getCourseTypes(syllabusName)
        );

        return modelAndViewEditSyllabusPage;
    }


    /**
     * <h3>url: /syllabusDraft/addYear/{syllabusName}</h3>
     *
     * @param syllabusName
     * @return redirects /syllabusDraft/edit/{syllabusName}
     */
    @GetMapping("/addYear/{syllabusName}")
    public ModelAndView addYear(
            @PathVariable("syllabusName") String syllabusName
    ) {
        syllabusDraftServices.addYear(syllabusName);

        return new ModelAndView("" +
                "redirect:/syllabusDraft/edit/" + syllabusName
        );
    }


    /**
     * <h3>url: /syllabusDraft/{syllabusName}/deleteYear/{yearId}</h3>
     *
     * @param syllabusName
     * @param yearId
     * @return redirects /syllabusDraft/edit/{syllabusName}
     */
    @GetMapping("/{syllabusName}/deleteYear/{yearId}")
    public ModelAndView deleteYear(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("yearId") Integer yearId
    ) {
        syllabusDraftServices.deleteYear(syllabusName, yearId);

        return new ModelAndView("" +
                "redirect:/syllabusDraft/edit/" + syllabusName
        );
    }

    /**
     * <h3>url: /syllabusDraft/{syllabusName}/addSemester/{yearId}</h3>
     *
     * @param syllabusName
     * @param yearId
     * @return redirects /syllabusDraft/edit/{syllabusName}
     */
    @GetMapping("/{syllabusName}/addSemester/{yearId}")
    public ModelAndView addSemesterIntoYear(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("yearId") Integer yearId
    ) {
        syllabusDraftServices.addSemesterIntoYear(syllabusName, yearId);

        return new ModelAndView("" +
                "redirect:/syllabusDraft/edit/" + syllabusName
        );
    }


    /**
     * <h3>url: /syllabusDraft/{syllabusName}/deleteSemester/{yearId}/{semesterId}</h3>
     *
     * @param syllabusName
     * @param yearId
     * @param semesterId
     * @return redirects /syllabusDraft/edit/{syllabusName}
     */
    @GetMapping("/{syllabusName}/deleteSemester/{yearId}/{semesterId}")
    public ModelAndView deleteSemesterFromYear(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("yearId") Integer yearId,
            @PathVariable("semesterId") Integer semesterId
    ) {
        syllabusDraftServices.deleteSemesterFromYear(
                syllabusName, yearId, semesterId
        );

        return new ModelAndView("" +
                "redirect:/syllabusDraft/edit/" + syllabusName
        );
    }

    /**
     * <h3>/syllabusDraft/{syllabusName}/addCourse/{yearId}/{semesterId}</h3>
     *
     * @param syllabusName
     * @param yearId
     * @param semesterId
     * @return redirects /syllabusDraft/edit/{syllabusName}
     */
    @PostMapping("/{syllabusName}/addCourse/{yearId}/{semesterId}")
    public ModelAndView addCourseIntoSemester(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("yearId") Integer yearId,
            @PathVariable("semesterId") Integer semesterId,
            @ModelAttribute("newCourse") Course course
    ) {
        course.initializeCourseCourseStructure(
                courseStructureServices.getContentBundleList(
                        syllabusName,
                        course.getCourseType()
                )
        );

        syllabusDraftServices.addCourseIntoSemester(
                syllabusName,
                yearId,
                semesterId,
                course
        );

        return new ModelAndView("" +
                "redirect:/syllabusDraft/edit/" + syllabusName
        );
    }


    /**
     * <h3>url: /syllabusDraft/{syllabusName}/deleteCourse/{yearId}/{semesterId}/{courseCode}</h3>
     *
     * @param syllabusName
     * @param yearId
     * @param semesterId
     * @param courseCode
     * @return redirects /syllabusDraft/edit/{syllabusName}
     */
    @GetMapping("/{syllabusName}/deleteCourse/{yearId}/{semesterId}/{courseCode}")
    public ModelAndView deleteCourseFromSemester(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("yearId") Integer yearId,
            @PathVariable("semesterId") Integer semesterId,
            @PathVariable("courseCode") String courseCode
    ) {
        syllabusDraftServices.deleteCourseFromSemester(
                syllabusName,
                yearId,
                semesterId,
                courseCode
        );

        return new ModelAndView("" +
                "redirect:/syllabusDraft/edit/" + syllabusName
        );
    }
}
