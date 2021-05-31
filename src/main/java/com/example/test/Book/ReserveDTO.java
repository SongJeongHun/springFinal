package com.example.test.Book;

public class ReserveDTO {
    int id;
    String userID;
    String title;

    public ReserveDTO(int id, String userID, String title) {
        this.id = id;
        this.userID = userID;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
