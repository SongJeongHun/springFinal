<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.sql.*" %>
<%
request.setCharacterEncoding("UTF-8");

    String userID=null;
    String userPassword=null;
    String userEmail=null;

    if(session.getAttribute("userID")!=null){
        PrintWriter script=response.getWriter();
        script.println("<script>");
        script.println("alert('로그인이 된 상태입니다.');");
        script.println("location.href='index.jsp'");
        script.println("</script>");
        script.close();
    }
%>
