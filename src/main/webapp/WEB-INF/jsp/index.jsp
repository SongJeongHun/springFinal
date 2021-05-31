<%@ page import="java.sql.*" %>
<%@ page import="java.net.InetAddress" %>
<%@ page import="com.example.test.Book.BookDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.test.Book.BookDTO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%
    InetAddress Address = InetAddress.getLocalHost();
    String ip = Address.getHostAddress();
    request.setCharacterEncoding("UTF-8");
    String searchType = "";
    int pages = 1;
    String search = "";
    if (request.getParameter("searchType") != null)
        searchType = request.getParameter("searchType");
    else
        searchType = "";
    if (request.getParameter("search") != null)
        search = request.getParameter("search");
    else
        search = "";
    if (request.getParameter("pages") != null) {
        pages = Integer.parseInt(request.getParameter("pages"));
        if (pages < 1)
            pages = 1;
    } else
        pages = 1;

    String userID = null;
    if (session.getAttribute("userID") != null) {
        userID = (String) session.getAttribute("userID");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text-html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>기말 프로젝트</title>
    <link rel="stylesheet" href="./css/bootstrap.min.css">
    <link rel="stylesheet" href="./css/custom.min.css">
</head>
<body>
<div class="row bg">
    <%--컨테이너 1--%>
    <section class="container col-2 p-5">
        <%
            if (userID != null) {
        %>
        <div class="dropdown">
            <button class="btn" id="dropdownButton" type="button" data-toggle="dropdown"
                    aria-expanded="false"><span style="color:#808080;"> <%=userID%>님! 안녕하세요</span>
            </button>
            <form method="get" action="/">
                <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownButton">
                    <div class="dropdown-item"><%=ip%>
                    </div>
                    <a class="dropdown-item" href="userLogout">로그아웃</a>
                    <a class="dropdown-item" href="MyPage">마이페이지</a>
                </ul>
            </form>
        </div>
        <%
        } else {
        %>
        <form method="post" action="./loginAction">
            <div class="form-group">
                <label>아이디</label>
                <input type="text" name="userID" class="form-control">
            </div>
            <div class="form-group">
                <label>비밀번호</label>
                <input type="password" name="userPwd" class="form-control">
            </div>
            <button type="submit" class="btn btn-primary">로그인</button>
            <a class="btn btn-primary" href="./userJoin">가입</a>
        </form>
        <%
            }
        %>
        <div class="card bg-light mt-3">
            <div class="card-header">
                <h5>실시간 대여 랭킹</h5>
            </div>
            <div class="card-body">

                <%
                    ArrayList<String> rank = new ArrayList<>();
                    BookDAO bookDAO = new BookDAO();
                    rank = bookDAO.rank();
                    for (int i = 0; i < rank.size(); i++) {
                %>
                <p><small><%=i + 1%>위 : <b><%=rank.get(i)%>
                </b></small></p>
                <%
                    }
                %>
            </div>
        </div>
    </section>
    <%--컨테이너 2--%>
    <section class="container col-10 pr-5">
        <form method="get" action="/" class="form-inline mt-3">
            <select name="searchType" class="form-control mx-1 mt-2">
                <option value="title" <%if (searchType.equals("title")) out.println("selected");%>>제목</option>
                <option value="author" <%if (searchType.equals("author")) out.println("selected");%>>저자</option>
            </select>
            <input type="text" name="search" class="form-control mx-1 mt-2" placeholder="내용을 입력하세요.">
            <button type="submit" class="btn btn-primary mx-1 mt-2">검색</button>
        </form>
        <%
            ArrayList<BookDTO> booksList = bookDAO.getBooks(searchType, search, pages);
            if (booksList.size() < 1) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('pages end.');");
                script.println("history.back();");
                script.println("</script>");
                script.close();
            }
        %>
        <div class="card bg-light mt-3">
            <div class="card-header">
                <h5>도서 목록</h5>
            </div>
            <div class="card-body">
                <label>제목</label>
                <%
                    if (booksList != null) {
                        for (int i = 0; i < booksList.size(); i++) {
                            if (booksList.get(i).getUsable()) {
                %>
                <a onclick="return confirm('대여하시겠습니까 ?')" href="LendingAction?bookID=<%=booksList.get(i).getID()%>"
                   class="form-control mt-2 text-left">
                        <%
                        }else{
                    %>
                    <a onclick="return confirm('예약하시겠습니까 ?')" href="ReserveAction?bookID=<%=booksList.get(i).getID()%>"
                       class="form-control mt-2 text-left">
                        <%
                            }
                        %>
                        <small>(<%=booksList.get(i).getID()%>)&nbsp;</small>
                        <b style>&nbsp; <%=booksList.get(i).getTitle()%>
                        </b>
                        <small>&nbsp; <%=booksList.get(i).getAuthor()%>
                        </small>
                        <small>&nbsp;&nbsp;출판사: <%=booksList.get(i).getPublisher()%>
                        </small>
                        <small>&nbsp;&nbsp;출판일: <%=booksList.get(i).getPubDate()%>
                        </small>
                        <%
                            if (booksList.get(i).getUsable()) {
                        %>
                        <small style="color:blue;">대여가능</small>
                        <%
                        } else {
                        %>
                        <small style="color:red;">대여불가</small>
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
        <div class="row text-center" style="width: 100%">
            <div style="width: 10%; float:none; margin:0 auto">
                <div class="row">
                    <button onclick="location.href = '/?pages=<%=pages - 1%>&searchType=<%=searchType%>&search=<%=search%>'"
                            class="btn btn-primary mt-3 mx-2"><
                    </button>
                    <button onclick="location.href = '/?pages=<%=pages + 1%>&searchType=<%=searchType%>&search=<%=search%>'"
                            class="btn btn-primary mt-3 mx-2">>
                    </button>
                </div>
            </div>
        </div>
    </section>
</div>
<%--jQuery 자바스크립트 추가--%>
<script src="./js/jquery-3.4.1.min.js"></script>
<%--파퍼 자바스크립트 추가--%>
<script src="./js/popper.js"></script>
<%--부트스트랩 자바스크립트 추가--%>
<script src="./js/bootstrap.min.js"></script>
</body>
</html>
</body>
</html>
