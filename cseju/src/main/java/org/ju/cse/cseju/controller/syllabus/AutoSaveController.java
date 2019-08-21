package org.ju.cse.cseju.controller.syllabus;

import org.ju.cse.cseju.message.RequestResponse;
import org.ju.cse.cseju.model.syllabus.Course;
import org.ju.cse.cseju.model.syllabus.CourseStructure;
import org.ju.cse.cseju.model.syllabus.content.Contents;
import org.ju.cse.cseju.service.syllabus.CourseServices;
import org.ju.cse.cseju.service.syllabus.CourseStructureServices;
import org.springframework.web.bind.annotation.*;

/**
 * @author Kamrul Hasan
 */
@RestController
@RequestMapping("/autoSave")
public class AutoSaveController {

    private CourseStructureServices
            courseStructureServices = new CourseStructureServices();

    private CourseServices courseServices = new CourseServices();

    /**
     * <h3>url: autoSave/course_structure</h3>
     * <p>Auto Save the changes in design form</p>
     *
     * @param courseStructure
     * @return RequestResponse
     */
    @PostMapping("/course_structure")
    public RequestResponse autoSaveCourseStructure(
            @RequestBody CourseStructure courseStructure
    ) {
        courseStructureServices.saveOrUpdate(
                courseStructure.getDatabaseName(),
                courseStructure
        );

        return new RequestResponse("saved", null);
    }


    @PostMapping("/course/{syllabusName}/{yearId}/{semesterId}/{courseCode}")
    public RequestResponse autoSaveCourseInputFormData(
            @PathVariable("syllabusName") String syllabusName,
            @PathVariable("yearId") Integer yearId,
            @PathVariable("semesterId") Integer semesterId,
            @PathVariable("courseCode") String courseCode,
            @RequestBody Contents contents
    ) {
        Course course = new Course();

        courseServices.saveCourseByCourseCode(
                syllabusName,
                yearId,
                semesterId,
                courseCode,
                course
        );

        return new RequestResponse("saved", null);
    }
}
