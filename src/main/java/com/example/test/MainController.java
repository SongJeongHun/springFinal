package com.example.test;

import com.example.test.User.userDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class MainController {
    userDAO userDAO;
        @RequestMapping("/")
        public ModelAndView Main(ModelAndView mav){
            mav.setViewName("index");
            return mav;
        }
        @RequestMapping("/userJoinAction")
    public ModelAndView userJoinAction(ModelAndView mav, HttpServletResponse response,
    @RequestParam(value = "userID",required = false)String id,
    @RequestParam(value = "userPwd",required = false)String pwd,
    @RequestParam(value = "userName",required = false)String name,
    @RequestParam(value = "userPhoneNum",required = false)String phoneNum,
    @RequestParam(value = "userAdd",required = false)String address) throws IOException {
            mav.setViewName("userJoinAction");
            if(id.equals("") || pwd.equals("") || name.equals("") || phoneNum.equals("") || address.equals("")) {
                PrintWriter script = response.getWriter();
                script.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
                script.println("<script>");
                script.println("alert('There are some items that have not been entered.');");
                script.println("history.back();");
                script.println("</script>");
                script.close();
            }else{
                if(userDAO.join(id,pwd,name,phoneNum,address) == 1){
                    PrintWriter script=response.getWriter();
                    script.println("<script>");
                    script.println("alert('UserJoin Success !!')");
                    script.println("location.href='index'");
                    script.println("</script>");
                    script.close();
                }else {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('This ID already exists.');");
                    script.println("history.back();");
                    script.println("</script>");
                    script.close();
                }

            }
            return mav;
        }
    @RequestMapping("/userJoin")
    public ModelAndView userJoin(ModelAndView mav){
        mav.setViewName("userJoin");
        return mav;
    }
}
