package com.example.test.Controller;

import com.example.test.User.userDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class UserController {
    @Autowired
    userDAO userDAO;
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
                script.println("location.href='/'");
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
    @RequestMapping("/loginAction")
    public ModelAndView userLoginAction(ModelAndView mav, HttpServletResponse response, HttpServletRequest request,
        @RequestParam(value = "userID",required = false)String id,
        @RequestParam(value = "userPwd",required = false)String pwd) throws IOException{
        mav.setViewName("loginAction");
        if(id.equals("") || pwd.equals("")){
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('There are some items that have not been entered.');");
            script.println("history.back();");
            script.println("</script>");
            script.close();
        }else{
            int result = userDAO.login(id,pwd);
            if(result == 1){
                HttpSession session = request.getSession();
                session.setAttribute("userID",id);
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('Login success !');");
                script.println("location.href='/'");
                script.println("</script>");
                script.close();
            }else if(result == 0){
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('Wrong pwd');");
                script.println("history.back();");
                script.println("</script>");
                script.close();
            }else if(result == -1){
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('Wrong id');");
                script.println("history.back();");
                script.println("</script>");
                script.close();
            }else if(result == -2){
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('DB error');");
                script.println("history.back();");
                script.println("</script>");
                script.close();
            }
        }
        return mav;
    }
    @RequestMapping("/userLogout")
    public ModelAndView userLogout(ModelAndView mav){
        mav.setViewName("userLogout");
        return mav;
    }
}
