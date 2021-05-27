package com.example.test.spring0525.ex9_3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ex9_3 {
    @RequestMapping("/login")
    public ModelAndView HelloController(ModelAndView mav){
        mav.setViewName("login");
        return mav;
    }
    @RequestMapping("/loginCheck")
    public ModelAndView loginCheck(ModelAndView mav,
        @RequestParam(value = "ID",required = false)String id,
        @RequestParam(value = "PWD",required = false)String pwd){
        if(id.equals("124sjh") && pwd.equals("qwer1234")){
            mav.addObject("LOGIN_OK","Welcome" + id);
        }else{
            mav.addObject("LOGIN_OK","Wrong pwd/id");
        }
        mav.setViewName("loginCheck");
        return mav;
    }
}