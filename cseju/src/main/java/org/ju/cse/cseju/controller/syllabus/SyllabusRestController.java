package org.ju.cse.cseju.controller.syllabus;

import org.ju.cse.cseju.service.syllabus.SyllabusServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tshr
 */
@RestController
@RequestMapping(path = "/syllabus/data")
public class SyllabusRestController {

    @Autowired
    private SyllabusServices syllabusServices;

    @GetMapping("/get/{syllabusName}")
    public String getSyllabusByName(
            @PathVariable("syllabusName") String syllabusName
    ) {
        return syllabusServices.getSyllabus(syllabusName);
    }

    @GetMapping("/{syllabusName}/add/year")
    public String addYear(
            @PathVariable("syllabusName") String syllabusName
    ) {
        syllabusServices.addYear(syllabusName);
        return "added";
    }
}
