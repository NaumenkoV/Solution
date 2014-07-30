package com.softserveinc.ita.service.impl;

import com.softserveinc.ita.entity.DataTransferFile;
import com.softserveinc.ita.exception.JcrException;
import com.softserveinc.ita.jcr.JcrDataAccess;
import com.softserveinc.ita.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;

public class DocumentServiceImpl implements DocumentService {
    private static final String DOCUMENT_SUFFIX = "-document";

    @Autowired
    private JcrDataAccess jcrDataAccess;

    private String getDocumentNodeName(String nodeName) {
        return nodeName + DOCUMENT_SUFFIX;
    }

    /**
     * Executes POST and PUT
     * @param dataTransferFile - document to save
     * @return - String with operation status
     * @throws com.softserveinc.ita.exception.JcrException
     */
    @Override
    public String postDocument(DataTransferFile dataTransferFile) throws JcrException {
        String tempDataTransferFileName = getDocumentNodeName(dataTransferFile.getNodeName());
        dataTransferFile.setNodeName(tempDataTransferFileName);
        return jcrDataAccess.post(dataTransferFile);
    }

    /**
     * Returns document
     * @param nodeName - name of document (Node name - applicantID)
     * @return - document of applicant
     * @throws com.softserveinc.ita.exception.JcrException
     */
    @Override
    public DataTransferFile getDocument(String nodeName) throws JcrException {
        String tempNodeName = getDocumentNodeName(nodeName);
        return jcrDataAccess.get(tempNodeName);
    }

    @Override
    public String deleteDocument(String nodeName) throws JcrException {
        String tempNodeName = getDocumentNodeName(nodeName);
        return jcrDataAccess.delete(tempNodeName);
    }
}
