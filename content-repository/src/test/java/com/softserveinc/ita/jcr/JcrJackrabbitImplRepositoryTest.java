package com.softserveinc.ita.jcr;

import com.softserveinc.ita.entity.DataTransferFile;
import com.softserveinc.ita.exception.JcrException;
import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.api.JackrabbitRepository;
import org.apache.jackrabbit.api.JackrabbitRepositoryFactory;
import org.apache.jackrabbit.core.RepositoryFactoryImpl;
import org.junit.Test;

import javax.jcr.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JcrJackrabbitImplRepositoryTest extends BaseJcrTest{

    private String repositoryHomeDir;
    private String repositoryConfigDir;

    public JcrJackrabbitImplRepositoryTest() {
        init();
    }

    public void init() {
        Properties prop = new Properties();
        InputStream input = null;
        String propertiesFileName = "repository.properties";
        try {

            input = this.getClass().getResourceAsStream("/" + propertiesFileName);

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            repositoryHomeDir = prop.getProperty("repo.homeDir");
            repositoryConfigDir = prop.getProperty("repo.confDir");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    JackrabbitRepositoryFactory rf = new RepositoryFactoryImpl();
    Properties prop = new Properties();
    Session session;

    /**
     * Necessary because JCR doesn't support PUT operation only POST,
     * that`s why we must check does this node exist or not
     * @param parent - parent Node - root
     * @param name - name of checked Node
     * @param type - type of checked Node in our case JcrConstants.NT_FILE or JcrConstants.NT_RESOURCE
     * @return - returns needed Node or creates new if It doesn`t exist
     * @throws javax.jcr.RepositoryException
     */
    public Node getOrAddNode(Node parent, String name, String type) throws RepositoryException {
        if (parent.hasNode(name)) {
            return parent.getNode(name);
        } else if (type != null) {
            return parent.addNode(name, type);
        } else {
            return parent.addNode(name);
        }
    }

    @Test
    public void testCreateANodeAndExpectedIsSuccess() {
        prop.setProperty("org.apache.jackrabbit.repository.home", repositoryHomeDir);
        prop.setProperty("org.apache.jackrabbit.repository.conf", repositoryConfigDir);
        try {
            String fileNameFromResources = "test-files/input-1024x768.jpg";
            InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
            byte[] input = IOUtils.toByteArray(is);
            String nodeName = "repositoryInitTest";
            DataTransferFile dataTransferFile = new DataTransferFile(nodeName, "test-files/input-1024x768.jpg","image/jpeg", input);

            Calendar lastModified = Calendar.getInstance();
            lastModified.setTimeInMillis(System.currentTimeMillis());

            ByteArrayInputStream bis = new ByteArrayInputStream(dataTransferFile.getContent());
            Binary binary;

            JackrabbitRepository repository = (JackrabbitRepository) rf.getRepository(prop);
            session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
            Node root = session.getRootNode();

            Node file = getOrAddNode(root, dataTransferFile.getNodeName(), "nt:file");
            Node content = getOrAddNode(file, "jcr:content", "nt:resource");

            binary = session.getValueFactory().createBinary(bis);
            content.setProperty("jcr:data", binary);
            content.setProperty("jcr:mimeType", dataTransferFile.getMimeType());
            content.setProperty("jcr:lastModified", lastModified);
            session.save();

        } catch (RepositoryException | IOException e) {
            e.printStackTrace();
        } finally {
                session.logout();
        }
    }

    @Test
    public void testGetANodeAndExpectedIsSuccess() throws JcrException, IOException {
        prop.setProperty("org.apache.jackrabbit.repository.home", repositoryHomeDir);
        prop.setProperty("org.apache.jackrabbit.repository.conf", repositoryConfigDir);
            DataTransferFile responseDataTransferFile = null;
            String nodeName = "repositoryInitTest";
            String fileNameFromResources = "test-files/input-1024x768.jpg";

            InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
            byte[] input = IOUtils.toByteArray(is);
            DataTransferFile dataTransferFile = new DataTransferFile(nodeName, "test-files/input-1024x768.jpg","image/jpeg", input);
        try {

            JackrabbitRepository repository = (JackrabbitRepository) rf.getRepository(prop);

            session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
            Node root = session.getRootNode();

            Node file;
            InputStream stream;
            String originalFileName;
            String mimeType;

            if (root.hasNode(nodeName)) {
                file=root.getNode(nodeName);
                Node content=file.getNode("jcr:content");
                String path = content.getPath();
                originalFileName = nodeName;
                mimeType = session.getNode(path).getProperty("jcr:mimeType").getString();
                Binary bin = session.getNode(path).getProperty("jcr:data").getBinary();
                stream = bin.getStream();

                responseDataTransferFile = new DataTransferFile(nodeName, originalFileName, mimeType, IOUtils.toByteArray(stream));
            } else {
                throw new JcrException("Some jcr trouble in JcrJackrabbitDataAccessImpl: method get - such node doesn`t exist");
            }

        } catch (RepositoryException | IOException e) {
            e.printStackTrace();
        } finally {
            session.logout();
        }
        assertNotNull(responseDataTransferFile);
        assertEquals(dataTransferFile.getNodeName(), responseDataTransferFile.getNodeName());
    }
}
