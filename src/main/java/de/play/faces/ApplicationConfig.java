package de.play.faces;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class ApplicationConfig implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(ApplicationConfig.class.getName());

    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.log(Level.INFO, "Server Info: " + sce.getServletContext().getServerInfo());
        LOGGER.log(Level.INFO, "Character Encoding request:" + sce.getServletContext().getRequestCharacterEncoding() + ", response: " + sce.getServletContext().getResponseCharacterEncoding());
        LOGGER.log(Level.INFO, "MajorVersion: " + sce.getServletContext().getEffectiveMajorVersion());
        LOGGER.log(Level.INFO, "MinorVersion: " + sce.getServletContext().getEffectiveMinorVersion());
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

}
