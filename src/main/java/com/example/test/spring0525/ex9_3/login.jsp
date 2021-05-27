<%--
  Created by IntelliJ IDEA.
  User: songjeonghun
  Date: 2021/05/26
  Time: 3:35 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<HTML>
<HEAD> <TITLE> request 객체 </TITLE> </HEAD> <BODY>
<Form Action = "../../../../../../webapp/WEB-INF/jsp/loginCheck" Method = "GET"> 아이디 : <Input Type = "Text" Name = "ID"> <BR/> 비밀번호 : <Input Type = "PassWord" Name = "PWD"> <BR/><BR/>
    <Input Type = "Submit" Value = "로그인" >
    <Input Type = "Reset" Value = "취소">
</Form>
</BODY>
</HTML>