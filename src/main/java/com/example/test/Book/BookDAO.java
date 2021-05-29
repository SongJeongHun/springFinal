package com.example.test.Book;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookDAO {
    public BookDAO(){}
    public ArrayList<BookDTO> getBooks(){
        ArrayList<BookDTO> books = new ArrayList<>();
        String SQL="SELECT * FROM books LIMIT 15";
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;//SQL에서 나온 값을 처리하기위한 클래스
        try{
            conn= getConnection(); //객체자체를 반환
            pstmt=conn.prepareStatement(SQL);   //컨객체의 SQL문장 준비
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
}
