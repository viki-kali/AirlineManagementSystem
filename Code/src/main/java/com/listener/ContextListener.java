package com.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Set Derby's system home using an absolute path.
        System.setProperty("derby.system.home", "D:\\Airline Management System");
        System.out.println("Derby system home set to: " + System.getProperty("derby.system.home"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup if needed.
    }
}
