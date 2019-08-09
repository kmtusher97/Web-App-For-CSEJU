package org.ju.cse.cseju.syllabus.controller;

import org.ju.cse.cseju.syllabus.model.CourseStructure;
import org.ju.cse.cseju.syllabus.service.CourseStructureServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Kamrul Hasan
 */
@Controller
@RequestMapping(path = "/course_structure")
public class CourseStructureController {

    private static final String SYLLABUS_VIEW_INPUT = "syllabus/input/";

    private CourseStructureServices courseStructureServices = new CourseStructureServices();

    /**
     * <h3>url: /course_structure/design/{databaseName}</h3>
     *
     * @param databaseName
     * @return ModelAndView CourseInputFromDesignPage
     */
    @GetMapping("/design/{databaseName}")
    public ModelAndView getCourseInputFromDesignPage(@PathVariable("databaseName") String databaseName) {
        ModelAndView modelAndViewDesignCourseInputForm = new ModelAndView(
                SYLLABUS_VIEW_INPUT + "courseInputFormDesignPage"
        );

        CourseStructure courseStructure = courseStructureServices.getCourseStructure(databaseName);
        modelAndViewDesignCourseInputForm.addObject("courseStructure", courseStructure);

        return modelAndViewDesignCourseInputForm;
    }

    /**
     * <h3>url: /course_structure/add_ContentBundle/{databaseName}</h3>
     *
     * @param databaseName
     * @return redirects to /course_structure/design/{databaseName}
     */
    @GetMapping("/add_ContentBundle/{databaseName}")
    public ModelAndView addContentBundle(@PathVariable("databaseName") String databaseName) {
        courseStructureServices.addContentBundle(databaseName);

        return new ModelAndView(
                "redirect:/course_structure/design/" + databaseName
        );
    }


    /**
     * <h3>url: /course_structure/delete_contentBundle/{databaseName}/{contentBundleIndex}</h3>
     *
     * @param databaseName
     * @param contentBundleIndex
     * @return redirects to /course_structure/design/{databaseName}
     */
    @GetMapping("/delete_contentBundle/{databaseName}/{contentBundleIndex}")
    public ModelAndView deleteContentBundle(
            @PathVariable("databaseName") String databaseName,
            @PathVariable("contentBundleIndex") Integer contentBundleIndex) {

        courseStructureServices.deleteContentBundleByContentBundleId(
                databaseName, contentBundleIndex
        );
        return new ModelAndView(
                "redirect:/course_structure/design/" + databaseName
        );
    }

    /**
     * @param databaseName
     * @param contentBundleIndex
     * @return redirects to /course_structure/design/{databaseName}
     */
    @GetMapping("/{databaseName}/add_field_/{contentBundleIndex}")
    public ModelAndView addFieldIntoTable(
            @PathVariable("databaseName") String databaseName,
            @PathVariable("contentBundleIndex") Integer contentBundleIndex
    ) {
        courseStructureServices.addFieldNameIntoTableByContentBundleId(
                databaseName,
                contentBundleIndex
        );

        return new ModelAndView(
                "redirect:/course_structure/design/" + databaseName
        );
    }


    /**
     * @param databaseName
     * @param contentBundleIndex
     * @param fieldNameId
     * @return redirects to /course_structure/design/{databaseName}
     */
    @GetMapping("/{databaseName}/delete_field_/{contentBundleIndex}/{fieldNameId}")
    public ModelAndView deleteFieldFromTable(
            @PathVariable("databaseName") String databaseName,
            @PathVariable("contentBundleIndex") Integer contentBundleIndex,
            @PathVariable("fieldNameId") Integer fieldNameId
    ) {
        courseStructureServices.deleteFieldNameFromTableByContentBundleId(
                databaseName,
                contentBundleIndex,
                fieldNameId
        );

        return new ModelAndView(
                "redirect:/course_structure/design/" + databaseName
        );
    }
}
