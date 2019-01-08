package com.marcobehler;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {



    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfiguration.class);

        DataSource dataSource = ctx.getBean(DataSource.class);
        try (Connection connection = dataSource.getConnection()) {
            ResultSet rs = connection.createStatement().executeQuery("select RANDOM_UUID() as random");
            while (rs.next()) {
                String myRandomNumberFromTheDb = rs.getString("random");
                System.out.println("myRandomNumberFromTheDb = " + myRandomNumberFromTheDb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("ppllaayyy");
    }


}
