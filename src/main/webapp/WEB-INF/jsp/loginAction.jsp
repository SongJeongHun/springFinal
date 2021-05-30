<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.PrintWriter" %>
<%
    request.setCharacterEncoding("UTF-8");

    String userID=null;
    String userPassword=null;

    if(request.getParameter("userID")==null)
        userID="";
    else
        userID=request.getParameter("userID");
    if(request.getParameter("userPassword")==null)
        userPassword="";
    else
        userPassword=request.getParameter("userPassword");
%>
