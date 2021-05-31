<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: songjeonghun
  Date: 2021/05/31
  Time: 4:28 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String userID = null;
    if (session.getAttribute("userID") != null) {
        userID = (String) session.getAttribute("userID");
    }
    if (userID == null) { //로그인을 하지 않은 상태라면
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('로그인을 해주세요.');");
        script.println("location.href='/'");
        script.println("</script>");
        script.close();
    }
%>