package com.marcobehler;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.annotation.PostConstruct;

public class TomcatLauncher {

    @PostConstruct
    public void start() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        Context ctx = tomcat.addContext("", null);
        Tomcat.addServlet(ctx, "testServlet", new HelloWorldServlet());
        ctx.addServletMappingDecoded("/", "testServlet");
        tomcat.start();

        new Thread(() -> tomcat.getServer().await()).start();

    }
}
