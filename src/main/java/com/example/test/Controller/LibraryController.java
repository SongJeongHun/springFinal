package com.example.test.Controller;

import com.example.test.Book.BookDAO;
import com.example.test.User.userDAO;
import com.mysql.cj.Session;
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
public class LibraryController {
    @Autowired
    BookDAO bookDAO;
    @RequestMapping("/LendingAction")
    public ModelAndView LendingAction(ModelAndView mav, HttpServletResponse response,HttpServletRequest request,
        @RequestParam(value = "bookID",required = false)int bookid) throws IOException {
        mav.setViewName("userJoinAction");
        String userID = null;
        HttpSession session = request.getSession();
        userID = (String)session.getAttribute("userID");
        if(userID == null){
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('This service requires a login.');");
            script.println("location.href='/'");
            script.println("</script>");
            script.close();
        }else{
            int result = bookDAO.lending(bookid,userID);
            if(result == 1){
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('Lending success.');");
                script.println("location.href='/'");
                script.println("</script>");
                script.close();
            } else if(result == -1){
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('DB error.');");
                script.println("location.href='/'");
                script.println("</script>");
                script.close();
            }else if(result == 0){
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('Already lended.');");
                script.println("location.href='/'");
                script.println("</script>");
                script.close();
            }
        }
        return mav;
    }
}
