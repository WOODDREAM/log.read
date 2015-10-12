package jettylog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User:huangtao
 * Date:2015-10-12
 * description：
 */
@RestController
@RequestMapping("/test")
public class Hello {
    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "helloWorld";
    }
}
