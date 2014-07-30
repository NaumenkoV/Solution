package com.softserveinc.ita.jcr.jackrabbit;

import com.softserveinc.ita.exception.JcrException;
import org.apache.jackrabbit.api.JackrabbitRepository;
import org.apache.jackrabbit.api.JackrabbitRepositoryFactory;
import org.apache.jackrabbit.core.RepositoryFactoryImpl;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import java.util.Properties;

/**
 * Class with creates a session required for work with repository
 */
public class JcrSessionCreator {
    JackrabbitRepositoryFactory rf = new RepositoryFactoryImpl();
    Properties prop = new Properties();
    Session session;

    private boolean xmlConfigHomeDir;
    private String config;
    private String homeDir;
    private String userRole;


    /**
     * Creates session...
     * @return - session entity
     * @throws com.softserveinc.ita.exception.JcrException
     */
    public Session getSession() throws JcrException {
        if(isXmlConfigHomeDir()) {
            prop.setProperty("org.apache.jackrabbit.repository.home", homeDir);
        } else {
            prop.setProperty("org.apache.jackrabbit.repository.home", getHomeDir());
        }
        prop.setProperty("org.apache.jackrabbit.repository.conf", config);
        try {
            JackrabbitRepository repository = (JackrabbitRepository) rf.getRepository(prop);

            session = repository.login(new SimpleCredentials(userRole, userRole.toCharArray()));

        } catch (RepositoryException e) {
            throw new JcrException("Error in JcrSessionCreator: [" + e.getMessage() + "]");
        }
        return session;
    }

    /**
     * Sets home dir
     * @param homeDir - home dir where the repository will be located
     */
    public void setHomeDir(String homeDir) {
        this.homeDir = homeDir;
    }

    /**
     * Sets configuration dir
     * @param config - path to the repository.xml (best of all if when config placed in a home dir)
     */
    public void setConfig(String config) {
        this.config = config;
    }

    /**
     * Sets user name
     * @param userRole - role of user (admin, etc.)
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getHomeDir() {
        return System.getProperty("catalina.home") + "/work/repository";
    }

    public boolean isXmlConfigHomeDir() {
        return xmlConfigHomeDir;
    }

    public void setXmlConfigHomeDir(boolean xmlConfigHomeDir) {
        this.xmlConfigHomeDir = xmlConfigHomeDir;
    }
}
