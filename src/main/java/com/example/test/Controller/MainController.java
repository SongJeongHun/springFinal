package com.example.test.Controller;

import com.example.test.User.userDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class MainController {
        @RequestMapping("/")
        public ModelAndView Main(ModelAndView mav){
            mav.setViewName("index");
            return mav;
        }
    @RequestMapping("/userJoin")
    public ModelAndView userJoin(ModelAndView mav){
        mav.setViewName("userJoin");
        return mav;
    }
}
