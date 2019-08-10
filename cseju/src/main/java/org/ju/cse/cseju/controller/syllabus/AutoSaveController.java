package org.ju.cse.cseju.controller.syllabus;

import org.ju.cse.cseju.message.RequestResponse;
import org.ju.cse.cseju.model.syllabus.CourseStructure;
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

    /**
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
}
