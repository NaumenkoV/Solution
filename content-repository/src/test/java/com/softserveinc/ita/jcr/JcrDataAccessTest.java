package com.softserveinc.ita.jcr;

import com.softserveinc.ita.entity.DataTransferFile;
import com.softserveinc.ita.exception.JcrException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;


public class JcrDataAccessTest extends BaseJcrTest {

    @Autowired
    private JcrDataAccess jcrDataAccess;

    @Test
    public void testCreateANodeAndExpectedIsNoExceptions() throws JcrException, IOException {
        String response;
        String fileNameFromResources = "test-files/input-1024x768.jpg";
        String nodeName = "123";

        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        byte[] input = IOUtils.toByteArray(is);
        DataTransferFile dataTransferFile = new DataTransferFile(nodeName, "test-files/input-1024x768.jpg","image/jpeg", input);

        response = jcrDataAccess.post(dataTransferFile);
        assertEquals("File added successfully " + nodeName, response);
        IOUtils.closeQuietly(is);
    }

    @Test
    public void testGetPostedImageAndExpectedIsOk() throws IOException, JcrException {
        String response;
        String fileNameFromResources = "test-files/input-1024x768.jpg";
        String nodeName = "124";

        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        byte[] input = IOUtils.toByteArray(is);
        DataTransferFile dataTransferFile = new DataTransferFile(nodeName, "test-files/input-1024x768.jpg","image/jpeg", input);

        response = jcrDataAccess.post(dataTransferFile);
        assertEquals("File added successfully " + nodeName, response);

        DataTransferFile responseImage = jcrDataAccess.get(nodeName);

        assertEquals(dataTransferFile.getNodeName(), responseImage.getNodeName());
        assertNotSame(dataTransferFile.getOriginalFileName(), responseImage.getOriginalFileName());
        assertEquals(dataTransferFile.getContent().length, responseImage.getContent().length);
        assertEquals(dataTransferFile.getMimeType(), responseImage.getMimeType());
        IOUtils.closeQuietly(is);
    }

    @Test
    public void testPostImageAndExpectedDataOverridingInASameNode() throws JcrException, IOException {
        String response1;
        String response2;
        String fileNameFromResources1 = "test-files/input-1024x768.jpg";
        String fileNameFromResources2 = "test-files/time-square.png";
        String nodeName = "125";

        InputStream is1 = this.getClass().getResourceAsStream("/" + fileNameFromResources1);
        byte[] input1 = IOUtils.toByteArray(is1);
        DataTransferFile dataTransferFile1 = new DataTransferFile(nodeName, "test-files/input-1024x768.jpg","image/jpeg", input1);

        response1 = jcrDataAccess.post(dataTransferFile1);
        assertEquals("File added successfully " + nodeName, response1);

        DataTransferFile responseImage1 = jcrDataAccess.get(nodeName);

        assertEquals(dataTransferFile1.getNodeName(), responseImage1.getNodeName());
        assertNotSame(dataTransferFile1.getOriginalFileName(), responseImage1.getOriginalFileName());
        assertEquals(dataTransferFile1.getContent().length, responseImage1.getContent().length);
        assertEquals(dataTransferFile1.getMimeType(), responseImage1.getMimeType());
        IOUtils.closeQuietly(is1);

        InputStream is2 = this.getClass().getResourceAsStream("/" + fileNameFromResources2);
        byte[] input2 = IOUtils.toByteArray(is2);
        DataTransferFile dataTransferFile2 = new DataTransferFile(nodeName, "test-files/input-1024x768.jpg","image/png", input2);

        response2 = jcrDataAccess.post(dataTransferFile2);
        assertEquals("File added successfully " + nodeName, response2);

        DataTransferFile responseImage2 = jcrDataAccess.get(nodeName);

        assertEquals(dataTransferFile2.getNodeName(), responseImage2.getNodeName());
        assertNotSame(dataTransferFile2.getOriginalFileName(), responseImage2.getOriginalFileName());
        assertEquals(dataTransferFile2.getContent().length, responseImage2.getContent().length);
        assertEquals(dataTransferFile2.getMimeType(), responseImage2.getMimeType());
        IOUtils.closeQuietly(is2);
    }

    @Test(expected = JcrException.class)
    public void testGetNonExistentImageAndExpectedJcrException() throws JcrException {
        String nodeName = "126";

        DataTransferFile responseImage = jcrDataAccess.get(nodeName);
        assertNull(responseImage);
    }

    @Test
    public void testDeletePostedImageAndExpectedIsOk() throws JcrException, IOException {
        String response;
        String deleteResponse;
        String fileNameFromResources = "test-files/input-1024x768.jpg";
        String nodeName = "127";

        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        byte[] input = IOUtils.toByteArray(is);
        DataTransferFile dataTransferFile = new DataTransferFile(nodeName, "test-files/input-1024x768.jpg","image/jpeg", input);

        response = jcrDataAccess.post(dataTransferFile);
        assertEquals("File added successfully " + nodeName, response);

        deleteResponse = jcrDataAccess.delete(nodeName);

        assertEquals("File successfully deleted " + nodeName, deleteResponse);
        IOUtils.closeQuietly(is);
    }

    @Test(expected = JcrException.class)
    public void testDeleteNonExistentImageAndExpectedJcrException() throws JcrException {
        String nodeName = "128";
        String deleteResponse = jcrDataAccess.delete(nodeName);
        assertNull(deleteResponse);
    }
}
