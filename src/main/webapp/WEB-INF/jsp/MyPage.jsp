<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.example.test.User.userDTO" %>
<%@ page import="com.example.test.User.userDAO" %>
<%@ page import="com.example.test.Book.BookDAO" %>
<%@ page import="com.example.test.Book.LendDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.example.test.Book.ReserveDTO" %><%--
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
    }else {
        userDAO userDAO = new userDAO();
        myInfo = userDAO.MyInfo(userID);
    }
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="./css/bootstrap.min.css">
    <link rel="stylesheet" href="./css/custom.min.css">
</head>
<body>
<div class="row bg">
<%--내 정보 컨테이너--%>
<section class="container col-2 p-3">
    <div class="card bg-light mt-5">
        <div class="card-header">
            <h5>내정보</h5>
        </div>
        <div class="card-body">
            <label><small>아이디</small></label>
            <a class="form-control mt-2 text-left">
                <small><%=myInfo.getUserID()%></small>
            </a>
            <label><small>이름</small></label>
            <a class="form-control mt-2 text-left">
                <small><%=myInfo.getUserName()%></small>
            </a>
            <label><small>휴대폰번호</small></label>
            <a class="form-control mt-2 text-left">
                <small><%=myInfo.getUserPhoneNum()%></small>
            </a>
            <label><small>주소</small></label>
            <a class="form-control mt-2 text-left">
                <small><%=myInfo.getUserAdd()%></small>
            </a>
        </div>
        <a class="btn btn-primary" data-toggle="modal" href="#modifyModal">정보수정</a>
    </div>

</section>
<%--대여 목록 컨테이너--%>
<section class="container col-9 p-3">
    <div class="card bg-light mt-5">
        <div class="card-header">
            <h5>대여 목록</h5>
        </div>
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            BookDAO bookDAO = new BookDAO();
            ArrayList<LendDTO> lendList = bookDAO.getLendList(userID);
            Date today = Calendar.getInstance().getTime();
        %>
        <div class="card-body">
            <%
                if (lendList != null) {
                    for (int i = 0; i < lendList.size(); i++) {
            %>
            <a onclick="return confirm('반납하시겠습니까 ?')" href = "ReturnAction?bookID=<%=lendList.get(i).getBookID()%>" class="form-control mt-2 text-left">
                <small>(<%=lendList.get(i).getBookID()%>)&nbsp;</small>
                <b><%=lendList.get(i).getBookTitle()%>&nbsp;</b>
                <small>&nbsp;대여일 : <%=lendList.get(i).getLendDate()%> </small>
                <b style>&nbsp;반납일 : <%=lendList.get(i).getReturnDate()%> </b>

                <%
                    if(today.after(sdf.parse(lendList.get(i).getReturnDate()))){
                %>
                <small style="color:red;">&nbsp;&nbsp;상태 : 연체</small>
                <%
                } else {
                %>
                <small style="color:green;">&nbsp;&nbsp;상태 : 정상</small>
                <%
                    }
                %>
            </a>
            <%
                    }
                }
            %>
        </div>
    </div>
    <div class="card bg-light mt-5">
        <div class="card-header">
            <h5>예약 목록</h5>
        </div>
        <%

            ArrayList<ReserveDTO> reserveList = bookDAO.getReserveList(userID);
        %>
        <div class="card-body">
            <%
                if (lendList != null) {
                    for (int i = 0; i < reserveList.size(); i++) {
            %>
            <a  class="form-control mt-2 text-left">
                <small>(<%=reserveList.get(i).getId()%>)&nbsp;</small>
                <b><%=reserveList.get(i).getTitle()%>&nbsp;</b>
            </a>
            <%
                    }
                }
            %>
        </div>
    </div>
</section>
</div>
<div class="modal fade" id="modifyModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modal">정보 수정</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="ModifyAction" method="post">
                    <div class="form-group">
                        <label>이름</label>
                        <input type="text" name="userName" class="form-control" maxlength="40">
                    </div>
                    <div class="form-group">
                        <label>휴대폰 번호</label>
                        <input type="text" name="userPhoneNum" class="form-control" maxlength="40">
                    </div>
                    <div class="form-group">
                        <label>주소</label>
                        <input type="text" name="userAdd" class="form-control" maxlength="40">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                        <button type="submit" class="btn btn-primary">수정완료</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%--jQuery 자바스크립트 추가--%>
<script src="./js/jquery-3.4.1.min.js"></script>
<%--파퍼 자바스크립트 추가--%>
<script src="./js/popper.js"></script>
<%--부트스트랩 자바스크립트 추가--%>
<script src="./js/bootstrap.min.js"></script>
</body>
</html>
