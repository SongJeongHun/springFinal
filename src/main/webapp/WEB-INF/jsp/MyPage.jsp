<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.example.test.User.userDTO" %>
<%@ page import="com.example.test.User.userDAO" %><%--
  Created by IntelliJ IDEA.
  User: songjeonghun
  Date: 2021/05/29
  Time: 6:37 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    userDTO myInfo = null;
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
    }else{
        userDAO userDAO = new userDAO();
        myInfo = userDAO.MyInfo(userID);
        System.out.println(myInfo.getUserID());
    }
    System.out.println(myInfo.getUserID());
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="./css/bootstrap.min.css">
    <link rel="stylesheet" href="./css/custom.min.css">
</head>
<body>
<section class="container col-2 p-5">
    <div class="card bg-light mt-3">
        <div class="card-header">
            <h5>내정보</h5>
        </div>
        <div class="card-body">
            <label>아이디</label>
            <a><%myInfo.getUserID();%></a>
            <label>이름</label>
            <a><%myInfo.getUserName();%></a>
            <label>휴대폰번호</label>
            <a><%myInfo.getUserPhoneNum();%></a>
            <label>주소</label>
            <a><%myInfo.getUserAdd();%></a>
        </div>
    </div>
</section>
<%--jQuery 자바스크립트 추가--%>
<script src="./js/jquery-3.4.1.min.js"></script>
<%--파퍼 자바스크립트 추가--%>
<script src="./js/popper.js"></script>
<%--부트스트랩 자바스크립트 추가--%>
<script src="./js/bootstrap.min.js"></script>
</body>
</html>
