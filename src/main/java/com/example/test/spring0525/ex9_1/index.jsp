<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: songjeonghun
  Date: 2021/05/25
  Time: 12:13 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head> <meta charset="EUC-KR"> </head> <body>
<h1> Index Page </h1>
<%=new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date()) %> </body>
</html>