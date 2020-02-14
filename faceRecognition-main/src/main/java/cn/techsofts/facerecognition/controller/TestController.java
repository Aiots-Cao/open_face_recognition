package cn.techsofts.facerecognition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Aiots-cao
 * @title: TestController
 * @projectName faceRecognition
 * @description: TODO
 * @date 2019-08-04 11:59
 */
@Controller
public class TestController {
    @RequestMapping("/hello")
    @ResponseBody
    public String test() {
        return "hello test++++";
    }

    @RequestMapping("/")
    public String start() {
        return "redirect:swagger-ui.html";
    }

}
