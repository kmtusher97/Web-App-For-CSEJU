package org.ju.cse.cseju.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author tshr
 */
@Controller
@RequestMapping(path = "/test1")
public class TestController1 {

    @GetMapping("/index")
    public ModelAndView getIndex() {
        return new ModelAndView("" +
                "syllabus/output/test.html"
        );
    }
}
