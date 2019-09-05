package org.ju.cse.cseju.controller.syllabus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author tshr
 */
@Controller
@RequestMapping(path = "/create/syllabus")
public class SyllabusCreatorController {

    private static final String SYLLABUS_VIEW_INPUT = "syllabus/input/";

    @GetMapping("/{syllabusName}")
    public ModelAndView getSyllabusEditorPage(
            @PathVariable("syllabusName") String syllabusName
    ) {
        ModelAndView  modelAndViewSyllabusEditor = new ModelAndView(
                SYLLABUS_VIEW_INPUT + "editSyllabusPage"
        );

        modelAndViewSyllabusEditor.addObject(syllabusName);

        return modelAndViewSyllabusEditor;
    }
}
