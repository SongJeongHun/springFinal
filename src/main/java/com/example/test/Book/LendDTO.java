package com.example.test.Book;

public class LendDTO {
    int bookID;
    String userID;
    String lendDate;
    String returnDate;
    String bookTitle;

    public LendDTO(int bookID, String userID, String lendDate, String returnDate, String bookTitle) {
        this.bookID = bookID;
        this.userID = userID;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
        this.bookTitle = bookTitle;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLendDate() {
        return lendDate;
    }

    public void setLendDate(String lendDate) {
        this.lendDate = lendDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
