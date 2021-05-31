package com.example.test.Book;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BookDAO {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    public BookDAO(){}
    public ArrayList<BookDTO> getBooks(String searchType,String search,int page){
        ArrayList<BookDTO> books = new ArrayList<>();
        String SQL="SELECT * FROM books WHERE CONCAT(title,author,publisher) LIKE ? ORDER BY ID ASC LIMIT " + (page - 1) * 13 +"," + 13;
        if (searchType.equals("title")) {
            SQL = "SELECT * FROM books WHERE CONCAT(title) LIKE ? ORDER BY ID ASC LIMIT " + (page - 1) * 13 +"," + 13;
        } else if (searchType.equals("author")) {
            SQL = "SELECT * FROM books WHERE CONCAT(author) LIKE ? ORDER BY ID ASC LIMIT " + (page - 1) * 13 +"," + 13;
        }
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;//SQL에서 나온 값을 처리하기위한 클래스
        try{
            conn= getConnection(); //객체자체를 반환
            pstmt=conn.prepareStatement(SQL);   //컨객체의 SQL문장 준비
            pstmt.setString(1, "%" + search + "%");
            rs = pstmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    int id = rs.getInt("ID");
                    String isbn = rs.getString("ISBN");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String publisher = rs.getString("publisher");
                    String pubDate = rs.getString("pubDate");
                    Boolean usable = rs.getBoolean("usable");
                    BookDTO book = new BookDTO(id,isbn,title,author,publisher,pubDate,usable);
                    books.add(book);
                }
            }
            return books;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{if(conn!=null) conn.close();} catch (Exception e ){e.printStackTrace();}    //conn과 밑에 3개는 한번사용후에 닫아주는 것이 필요
            try{if(pstmt!=null) pstmt.close();} catch (Exception e ){e.printStackTrace();}
            try{if(rs!=null) rs.close();} catch (Exception e ){e.printStackTrace();}
        }
        return null;
    }
    public int returning(int bookID){
        String SQL1="UPDATE books SET usable=1 WHERE id=" + bookID;
        String SQL2="DELETE FROM lendtable WHERE id=" + bookID;
        String SQL3="SELECT * FROM reservetable WHERE id=" + bookID;
        String SQL4="DELETE FROM reservetable WHERE id=" + bookID;
        Connection conn=null;
        PreparedStatement pstmt1=null;
        PreparedStatement pstmt2=null;
        PreparedStatement pstmt3=null;
        PreparedStatement pstmt4=null;
        ResultSet rs = null;
        try{
            conn= getConnection(); //객체자체를 반환
            pstmt1=conn.prepareStatement(SQL1);   //컨객체의 SQL문장 준비
            pstmt1.executeUpdate();
            pstmt2 = conn.prepareStatement(SQL2);
            pstmt2.executeUpdate();
            pstmt3 = conn.prepareStatement(SQL3);
            pstmt4 = conn.prepareStatement(SQL4);
            rs = pstmt3.executeQuery();
            if (rs.next()) {
                String userID = rs.getString("userID");
                lending(bookID,userID);
                pstmt4.executeUpdate();
            }
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{if(conn!=null) conn.close();} catch (Exception e ){e.printStackTrace();}    //conn과 밑에 3개는 한번사용후에 닫아주는 것이 필요
            try{if(pstmt1!=null) pstmt1.close();} catch (Exception e ){e.printStackTrace();}
            try{if(pstmt2!=null) pstmt2.close();} catch (Exception e ){e.printStackTrace();}
            try{if(rs!=null) rs.close();} catch (Exception e ){e.printStackTrace();}
            try{if(pstmt3!=null) pstmt3.close();} catch (Exception e ){e.printStackTrace();}
        }
        return -1;
    }
    public int reserving(int bookID,String userID){
        String SQL="INSERT INTO reservetable VALUES (?,?,?)";
        Connection conn=null;
        PreparedStatement pstmt=null;
        try{
            conn= getConnection(); //객체자체를 반환
            pstmt=conn.prepareStatement(SQL);   //컨객체의 SQL문장 준비
            pstmt.setInt(1,bookID);
            pstmt.setString(2,userID);
            pstmt.setString(3,getTitle(bookID));
            return pstmt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{if(conn!=null) conn.close();} catch (Exception e ){e.printStackTrace();}    //conn과 밑에 3개는 한번사용후에 닫아주는 것이 필요
            try{if(pstmt!=null) pstmt.close();} catch (Exception e ){e.printStackTrace();}
        }
        return -1;
    }
    public int lending(int bookID,String userID){
        Calendar time = Calendar.getInstance();
        if(!usableCheck(bookID))
            return 0;
        String SQL1="UPDATE books SET usable=0, lendCount = lendCount + 1 WHERE id=" + bookID;
        String SQL2="INSERT INTO lendtable VALUES (?,?,?,?,?)";
        Connection conn=null;
        PreparedStatement pstmt1=null;
        PreparedStatement pstmt2=null;
        try{
            conn= getConnection(); //객체자체를 반환
            pstmt1=conn.prepareStatement(SQL1);   //컨객체의 SQL문장 준비
            pstmt1.executeUpdate();
            pstmt2 = conn.prepareStatement(SQL2);
            pstmt2.setInt(1,bookID);
            pstmt2.setString(2,userID);
            pstmt2.setString(3,sdf.format(time.getTime()));
            time.add(Calendar.DATE,14);
            pstmt2.setString(4,sdf.format(time.getTime()));
            pstmt2.setString(5,getTitle(bookID));
            return pstmt2.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{if(conn!=null) conn.close();} catch (Exception e ){e.printStackTrace();}    //conn과 밑에 3개는 한번사용후에 닫아주는 것이 필요
            try{if(pstmt1!=null) pstmt1.close();} catch (Exception e ){e.printStackTrace();}
            try{if(pstmt2!=null) pstmt2.close();} catch (Exception e ){e.printStackTrace();}
        }
        return -1;
    }
    public ArrayList<String> rank() {
        ArrayList<String> result = new ArrayList<>();
        String SQL = "SELECT title FROM books ORDER BY lendCount DESC LIMIT 5";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;//SQL에서 나온 값을 처리하기위한 클래스
        try {
            conn = getConnection(); //객체자체를 반환
            pstmt = conn.prepareStatement(SQL);   //컨객체의 SQL문장 준비
            rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    result.add(title);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }    //conn과 밑에 3개는 한번사용후에 닫아주는 것이 필요
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public boolean usableCheck(int bookid){
        boolean result = false;
        String SQL="SELECT usable FROM books WHERE ID="+bookid;
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;//SQL에서 나온 값을 처리하기위한 클래스
        try{
            conn= getConnection(); //객체자체를 반환
            pstmt=conn.prepareStatement(SQL);   //컨객체의 SQL문장 준비
            rs = pstmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    result = rs.getBoolean("usable");
                }
            }
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{if(conn!=null) conn.close();} catch (Exception e ){e.printStackTrace();}    //conn과 밑에 3개는 한번사용후에 닫아주는 것이 필요
            try{if(pstmt!=null) pstmt.close();} catch (Exception e ){e.printStackTrace();}
            try{if(rs!=null) rs.close();} catch (Exception e ){e.printStackTrace();}
        }
        return result;
    }
    public String getTitle(int bookid){
        String result = "";
        String SQL="SELECT title FROM books WHERE ID="+bookid;
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;//SQL에서 나온 값을 처리하기위한 클래스
        try{
            conn= getConnection(); //객체자체를 반환
            pstmt=conn.prepareStatement(SQL);   //컨객체의 SQL문장 준비
            rs = pstmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    result = rs.getString("title");
                }
            }
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{if(conn!=null) conn.close();} catch (Exception e ){e.printStackTrace();}    //conn과 밑에 3개는 한번사용후에 닫아주는 것이 필요
            try{if(pstmt!=null) pstmt.close();} catch (Exception e ){e.printStackTrace();}
            try{if(rs!=null) rs.close();} catch (Exception e ){e.printStackTrace();}
        }
        return result;
    }
    public Connection getConnection(){
        try{
            String dbURL="jdbc:mysql://localhost:3306/BookManagementSystem?serverTimezone=UTC";
            String dbID="root";
            String dbPassword="qwer1234";
            Class.forName("com.mysql.cj.jdbc.Driver");
            //그냥jdbc에 드라이버는 이제 안씀
            return DriverManager.getConnection(dbURL,dbID,dbPassword);//3306 포트에 튜토리얼 에서 위에 적힌 아이디와
            // 페스워드로 로그인한 상태를 반환e
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<LendDTO> getLendList(String userID){
        ArrayList<LendDTO> lendTable = new ArrayList<>();
        String SQL="SELECT * FROM lendtable WHERE userID=?";
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;//SQL에서 나온 값을 처리하기위한 클래스
        try{
            conn= getConnection(); //객체자체를 반환
            pstmt=conn.prepareStatement(SQL);   //컨객체의 SQL문장 준비
            pstmt.setString(1,userID);
            rs = pstmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    int bookID = rs.getInt("ID");
                    String userid = rs.getString("userID");
                    String lendDate = rs.getString("lendDate");
                    String returnDate = rs.getString("returnDate");
                    String bookTItle = rs.getString("title");
                    lendTable.add(new LendDTO(bookID,userid,lendDate,returnDate,bookTItle));
                }
            }
            return lendTable;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{if(conn!=null) conn.close();} catch (Exception e ){e.printStackTrace();}    //conn과 밑에 3개는 한번사용후에 닫아주는 것이 필요
            try{if(pstmt!=null) pstmt.close();} catch (Exception e ){e.printStackTrace();}
            try{if(rs!=null) rs.close();} catch (Exception e ){e.printStackTrace();}
        }
        return lendTable;
    }
    public ArrayList<ReserveDTO> getReserveList(String userID){
        ArrayList<ReserveDTO> reserveList = new ArrayList<>();
        String SQL="SELECT * FROM reservetable WHERE userID=?";
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;//SQL에서 나온 값을 처리하기위한 클래스
        try{
            conn= getConnection(); //객체자체를 반환
            pstmt=conn.prepareStatement(SQL);   //컨객체의 SQL문장 준비
            pstmt.setString(1,userID);
            rs = pstmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    int bookID = rs.getInt("ID");
                    String userid = rs.getString("userID");
                    String title = rs.getString("title");
                    reserveList.add(new ReserveDTO(bookID,userid,title));
                }
            }
            return reserveList;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{if(conn!=null) conn.close();} catch (Exception e ){e.printStackTrace();}    //conn과 밑에 3개는 한번사용후에 닫아주는 것이 필요
            try{if(pstmt!=null) pstmt.close();} catch (Exception e ){e.printStackTrace();}
            try{if(rs!=null) rs.close();} catch (Exception e ){e.printStackTrace();}
        }
        return reserveList;
    }
}
