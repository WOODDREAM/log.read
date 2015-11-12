package jettylog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User:huangtao
 * Date:2015-10-12
 * description：
 */
@Controller
@RequestMapping(value = "/test")
public class Hello {
//    @ResponseBody
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
//        return new ModelAndView("helloWorld");
        return "helloWorld";
    }
}
