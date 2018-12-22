package com.marcobehler;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.annotation.PostConstruct;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Main {

    public static class TomcatOnClassPathCondition implements Condition {
        @Override
        public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
            try {
                Class.forName("org.apache.catalina.startup.Tomcat");
                return true;
            } catch (ClassNotFoundException e) {
               return false;
            }
        }
    }

    public static class MyConfiguration {

        @Bean
        @Conditional(value = TomcatOnClassPathCondition.class)
        public TomcatLauncher tomcatLauncher() {
            return new TomcatLauncher();
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfiguration.class);
        System.out.println("ppllaayyy");
    }


    public static class TomcatLauncher {

        @PostConstruct
        public void start() throws LifecycleException {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(8080);

            Context ctx = tomcat.addContext("", null);
            Tomcat.addServlet(ctx, "testServlet", new HelloWorldServlet() );
            ctx.addServletMappingDecoded("/", "testServlet");
            tomcat.start();

            new Thread(() -> tomcat.getServer().await()).start();

        }
    }

    private static class HelloWorldServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.getWriter().println("<html><body><h1>YAY! THIS STUFF WORKS</h1></body></html>");
        }
    }
}
