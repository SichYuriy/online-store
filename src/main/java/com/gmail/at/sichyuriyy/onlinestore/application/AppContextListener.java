package com.gmail.at.sichyuriyy.onlinestore.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by Yuriy on 4/7/2017.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger LOGGER
            = LogManager.getLogger(AppContextListener.class);

    private WebApplication webApplication = new WebApplication();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("webApplication", webApplication);
        webApplication.setServletContext(sce.getServletContext());
        webApplication.init();

        LOGGER.info("WebApplication initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
