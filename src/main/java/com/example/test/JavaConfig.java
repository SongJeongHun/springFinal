package com.example.test;

import com.example.test.Book.BookDAO;
import com.example.test.User.userDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {
    @Bean
    public userDAO userDAO(){
        return new userDAO();
    }
    @Bean
    public BookDAO bookDAO(){
        return new BookDAO();
    }
}
