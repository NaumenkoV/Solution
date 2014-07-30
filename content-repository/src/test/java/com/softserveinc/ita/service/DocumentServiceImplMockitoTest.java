package com.softserveinc.ita.service;

import com.softserveinc.ita.entity.DataTransferFile;
import com.softserveinc.ita.exception.JcrException;
import com.softserveinc.ita.exception.NotExistingNodeJcrException;
import com.softserveinc.ita.jcr.JcrDataAccess;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.io.InputStream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DocumentServiceImplMockitoTest extends BaseServiceTest {
    private static final String DOCUMENT_SUFFIX = "-document";

    @Autowired
    @Mock
    private JcrDataAccess jcrDataAccess;

    @Autowired
    @InjectMocks
    private DocumentService documentService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPostDocumentAndExpectedIsOk() throws IOException, JcrException {
        String nodeName = "testDocumentService100";
        String requestedNodeName = nodeName + DOCUMENT_SUFFIX;

        String fileNameFromResources = "test-files/plain_text.txt";
        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        byte[] input = IOUtils.toByteArray(is);
        DataTransferFile dataTransferFile = new DataTransferFile(requestedNodeName, "test-files/plain_text.txt", "text/plain", input);

        when(jcrDataAccess.post(dataTransferFile)).thenReturn("File added successfully ");
        String response = documentService.postDocument(dataTransferFile);
        verify(jcrDataAccess, times(1)).post(dataTransferFile);
        assertEquals("File added successfully ", response);

        IOUtils.closeQuietly(is);
    }

    @Test
    public void testGetDocumentAndExpectedIsOk() throws JcrException, IOException {
        String nodeName = "testDocumentService100";
        String requestedNodeName = nodeName + DOCUMENT_SUFFIX;

        String fileNameFromResources = "test-files/plain_text.txt";
        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        byte[] input = IOUtils.toByteArray(is);
        DataTransferFile dataTransferFile = new DataTransferFile(requestedNodeName, "test-files/plain_text.txt","text/plain", input);

        when(jcrDataAccess.get(requestedNodeName)).thenReturn(dataTransferFile);

        DataTransferFile responseFile = documentService.getDocument(nodeName);

        verify(jcrDataAccess, times(1)).get(requestedNodeName);
        assertEquals(dataTransferFile.getContent().length, responseFile.getContent().length);
        IOUtils.closeQuietly(is);
    }

    @Test(expected = NotExistingNodeJcrException.class)
    public void testGetNonExistentDocumentAndExpectedIsJcrException() throws JcrException, IOException {
        String nodeName = "testDocumentServiceNonExistent";
        String requestedNodeName = nodeName + DOCUMENT_SUFFIX;

        when(jcrDataAccess.get(requestedNodeName)).thenThrow(new NotExistingNodeJcrException());

        DataTransferFile responseFile = documentService.getDocument(nodeName);

        verify(jcrDataAccess, times(1)).get(requestedNodeName);
        assertNull(responseFile);
    }

    @Test
    public void testDeleteDocumentAndExpectedIsOk() throws JcrException, IOException {
        String nodeName = "testDocumentService101";
        String requestedNodeName = nodeName + DOCUMENT_SUFFIX;

        String fileNameFromResources = "test-files/plain_text.txt";
        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        byte[] input = IOUtils.toByteArray(is);
        DataTransferFile dataTransferFile = new DataTransferFile(requestedNodeName, "test-files/plain_text.txt","text/plain", input);

        when(jcrDataAccess.post(dataTransferFile)).thenReturn("File added successfully ");
        String response = documentService.postDocument(dataTransferFile);
        verify(jcrDataAccess, times(1)).post(dataTransferFile);
        assertEquals("File added successfully ", response);

        IOUtils.closeQuietly(is);

        when(jcrDataAccess.delete(requestedNodeName)).thenReturn("File successfully deleted");
        String delStatus = documentService.deleteDocument(nodeName);
        verify(jcrDataAccess, times(1)).delete(requestedNodeName);
        assertEquals("File successfully deleted", delStatus);
    }

    @Test(expected = NotExistingNodeJcrException.class)
    public void TestDeleteNonExistentDocumentAndExpectedJcrException() throws JcrException {
        String nodeName = "testDocumentServiceNonExistent";
        String requestedNodeName = nodeName + DOCUMENT_SUFFIX;

        when(jcrDataAccess.delete(requestedNodeName)).thenThrow(new NotExistingNodeJcrException());
        String delStatus = documentService.deleteDocument(nodeName);
        verify(jcrDataAccess, times(1)).delete(requestedNodeName);
        assertNull(delStatus);
    }
}
