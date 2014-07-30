package com.softserveinc.ita.jcr.jackrabbit;

import com.softserveinc.ita.entity.DataTransferFile;
import com.softserveinc.ita.exception.JcrException;
import com.softserveinc.ita.exception.NotExistingNodeJcrException;
import com.softserveinc.ita.jcr.JcrDataAccess;
import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.JcrConstants;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class JcrJackrabbitDataAccessImpl implements JcrDataAccess {
    Session session;

    @Autowired
    private JcrSessionCreator jcrSession;

    /**
     * Creates or updates the named node (we will use applicantID`s as Node names)
     * this method handles POST & PUT operations because JCR doesn`t support PUT operation
     * for more details see: org.apache.jackrabbit.commons.JcrUtils
     * @param dataTransferFile - instance that will be saved in repository
     * @return String with status info
     * @throws com.softserveinc.ita.exception.JcrException
     */
    @Override
    public String post(DataTransferFile dataTransferFile) throws JcrException {
        String response = null;

        Binary binary = null;
        Calendar lastModified = Calendar.getInstance();
        lastModified.setTimeInMillis(System.currentTimeMillis());

        try {
            session = jcrSession.getSession();
            Node root = session.getRootNode();

            ByteArrayInputStream bis = new ByteArrayInputStream(dataTransferFile.getContent());
            binary = session.getValueFactory().createBinary(bis);

            Node file = getOrAddNode(root, dataTransferFile.getNodeName(), JcrConstants.NT_FILE);
            Node content = getOrAddNode(file, Node.JCR_CONTENT, JcrConstants.NT_RESOURCE);

            content.setProperty(JcrConstants.JCR_DATA, binary);
            content.setProperty(JcrConstants.JCR_MIMETYPE, dataTransferFile.getMimeType());
            content.setProperty(JcrConstants.JCR_LASTMODIFIED, lastModified);
            session.save();

            response = "File added successfully " + dataTransferFile.getNodeName();

        } catch (RepositoryException e) {
            throw new JcrException("In JcrJackrabbitDataAccessImpl: method post ["
                    + e.getMessage() + "]");
        } finally {
            if(binary != null) {
                binary.dispose();
            }
            session.logout();
        }
        return response;
    }

    /**
     * Necessary because JCR doesn`t support PUT operation only POST,
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

    /**
     * Allows to get data from node
     * @param nodeName - name of required Node
     * @return - new ImageFile with necessary data
     * @throws com.softserveinc.ita.exception.JcrException
     */
    @Override
    public DataTransferFile get(String nodeName) throws JcrException {
        DataTransferFile responseDataTransferFile = null;

        try {
            session = jcrSession.getSession();
            Node root = session.getRootNode();

            Node file;
            InputStream stream;
            String originalFileName;
            String mimeType;

            if (root.hasNode(nodeName)) {
                file=root.getNode(nodeName);
                Node content=file.getNode(JcrConstants.JCR_CONTENT);
                String path = content.getPath();
                Binary bin = session.getNode(path).getProperty(JcrConstants.JCR_DATA).getBinary();
                mimeType = session.getNode(path).getProperty(JcrConstants.JCR_MIMETYPE).getString();
                originalFileName = nodeName;
                stream = bin.getStream();

                responseDataTransferFile = new DataTransferFile(nodeName, originalFileName, mimeType,
                                                                IOUtils.toByteArray(stream));
            } else {
                throw new NotExistingNodeJcrException(nodeName);
            }

        } catch (RepositoryException | IOException e) {
            throw new JcrException("In JcrJackrabbitDataAccessImpl: method get [" + e.getMessage() + "]");
        } finally {
            session.logout();
        }
        return responseDataTransferFile;
    }

    /**
     * Deletes Node if we do not need It anymore
     * @param nodeName - name of Node
     * @return - String with status info
     * @throws com.softserveinc.ita.exception.JcrException
     */
    @Override
    public String delete(String nodeName) throws JcrException {
        String delStatus = null;
        try {
            session = jcrSession.getSession();
            Node root = session.getRootNode();
            Node file;
            if (root.hasNode(nodeName)) {
                file = root.getNode(nodeName);
                file.remove();
                session.save();

                delStatus = "File successfully deleted " + nodeName;
            } else {
                throw new NotExistingNodeJcrException(nodeName);
            }
        } catch (RepositoryException e) {
            throw new JcrException("In JcrJackrabbitDataAccessImp: method delete [" + e.getMessage() + "]");
        } finally {
            session.logout();
        }
        return delStatus;
    }
}
