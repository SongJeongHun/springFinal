package com.example.test.spring0525.ex9_2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Hello {
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(Model model,
                        @RequestParam(value = "name",required = false)String name) {
        model.addAttribute("greeting","안녕하세요" + name);
        return "hello";
    }
}