package com.softserveinc.ita.service;

import com.softserveinc.ita.entity.Base64DataTransferFile;
import com.softserveinc.ita.entity.DataTransferFile;
import com.softserveinc.ita.exception.JcrException;
import com.softserveinc.ita.exception.NotExistingNodeJcrException;
import com.softserveinc.ita.imageprocessing.ImageProcessor;
import com.softserveinc.ita.jcr.JcrDataAccess;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ImageServiceImplMockitoTest extends BaseServiceTest {
    private static final String IMAGE_SUFFIX = "-image";

    @Autowired
    @Mock
    private JcrDataAccess jcrDataAccess;

    @Autowired
    @Mock
    private ImageProcessor imageProcessor;

    @Autowired
    @InjectMocks
    private ImageService imageService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPostImageAndExpectedIsOk() throws IOException, JcrException {
        String nodeName = "testImageService100";
        String requestedNodeName = nodeName + IMAGE_SUFFIX;

                String fileNameFromResources = "test-files/input-1024x768.jpg";
        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        byte[] input = IOUtils.toByteArray(is);
        DataTransferFile dataTransferFile = new DataTransferFile(requestedNodeName, "test-files/input-1024x768.jpg","image/jpeg", input);

        when(jcrDataAccess.post(dataTransferFile)).thenReturn("File added successfully ");
        String response = imageService.postImage(dataTransferFile);
        verify(jcrDataAccess, times(1)).post(dataTransferFile);
        assertEquals("File added successfully ", response);

        IOUtils.closeQuietly(is);
    }

    @Test
    public void testGetImageAndExpectedIsOk() throws JcrException, IOException {
        String nodeName = "testImageService101";
        String requestedNodeName = nodeName + IMAGE_SUFFIX;
        int height = 200;
        int width = 200;

        String fileNameFromResources = "test-files/input-1024x768.jpg";
        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        byte[] input = IOUtils.toByteArray(is);
        DataTransferFile dataTransferFile = new DataTransferFile(requestedNodeName, "test-files/input-1024x768.jpg","image/jpeg", input);

            BufferedImage img = ImageIO.read(new ByteArrayInputStream(input));
            BufferedImage scaledImg = Scalr.resize(img, Scalr.Mode.AUTOMATIC, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(scaledImg, "jpg", baos);
            byte[] imageInByte = baos.toByteArray();
            baos.flush();
            baos.close();

        DataTransferFile scaledPic = new DataTransferFile(nodeName, "test-files/input-1024x768.jpg","image/jpeg", imageInByte);

        when(jcrDataAccess.get(requestedNodeName)).thenReturn(dataTransferFile);
        when(imageProcessor.resize(dataTransferFile, dataTransferFile.getMimeType(), width, height)).thenReturn(scaledPic);

        DataTransferFile responseFile = imageService.getImage(nodeName, width, height);

        verify(jcrDataAccess, times(1)).get(requestedNodeName);
        verify(imageProcessor, times(1)).resize(dataTransferFile, dataTransferFile.getMimeType(), width, height);
        assertEquals(scaledPic.getContent().length, responseFile.getContent().length);

        IOUtils.closeQuietly(is);
    }

    @Test
    public void testGetImageInOriginalSizeAndExpectedIsOk() throws JcrException, IOException {
        String nodeName = "testImageService101";
        String requestedNodeName = nodeName + IMAGE_SUFFIX;

        String fileNameFromResources = "test-files/input-1024x768.jpg";
        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        byte[] input = IOUtils.toByteArray(is);
        DataTransferFile dataTransferFile = new DataTransferFile(requestedNodeName, "test-files/input-1024x768.jpg","image/jpeg", input);

        when(jcrDataAccess.get(requestedNodeName)).thenReturn(dataTransferFile);

        DataTransferFile responseFile = imageService.getImage(nodeName);

        verify(jcrDataAccess, times(1)).get(requestedNodeName);
        assertEquals(dataTransferFile.getContent().length, responseFile.getContent().length);
        IOUtils.closeQuietly(is);
    }

    @Test(expected = NotExistingNodeJcrException.class)
    public void testGetNonExistentImageAndExpectedJcrException() throws JcrException, IOException {
        String nodeName = "testImageServiceNonExistent";
        String requestedNodeName = nodeName + IMAGE_SUFFIX;
        int height = 200;
        int width = 200;

        when(jcrDataAccess.get(requestedNodeName)).thenThrow(new NotExistingNodeJcrException());
        DataTransferFile responseFile = imageService.getImage(nodeName, width, height);
        verify(jcrDataAccess, times(1)).get(requestedNodeName);
        assertNull(responseFile);
    }

    @Test
    public void testDeleteImageAndExpectedIsOk() throws JcrException, IOException {
        String nodeName = "testImageService102";
        String requestedNodeName = nodeName + IMAGE_SUFFIX;

        String fileNameFromResources = "test-files/input-1024x768.jpg";
        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        byte[] input = IOUtils.toByteArray(is);
        DataTransferFile dataTransferFile = new DataTransferFile(requestedNodeName, "test-files/input-1024x768.jpg","image/jpeg", input);

        when(jcrDataAccess.post(dataTransferFile)).thenReturn("File added successfully ");
        String response = imageService.postImage(dataTransferFile);
        verify(jcrDataAccess, times(1)).post(dataTransferFile);
        assertEquals("File added successfully ", response);

        IOUtils.closeQuietly(is);

        when(jcrDataAccess.delete(requestedNodeName)).thenReturn("File successfully deleted");
        String delStatus = imageService.deleteImage(nodeName);
        verify(jcrDataAccess, times(1)).delete(requestedNodeName);
        assertEquals("File successfully deleted", delStatus);
    }

    @Test(expected = NotExistingNodeJcrException.class)
    public void TestDeleteNonExistentImageAndExpectedJcrException() throws JcrException {
        String nodeName = "testImageServiceNonExistent";
        String requestedNodeName = nodeName + IMAGE_SUFFIX;

        when(jcrDataAccess.delete(requestedNodeName)).thenThrow(new NotExistingNodeJcrException());
        String delStatus = imageService.deleteImage(nodeName);
        verify(jcrDataAccess, times(1)).delete(requestedNodeName);
        assertNull(delStatus);
    }

    @Test
    public void testPostImage64AndExpectedIsOk() throws IOException, JcrException {
        String nodeName = "testImageService103";
        String requestedNodeName = nodeName + IMAGE_SUFFIX;

        String fileNameFromResources = "test-files/input-1024x768.jpg";
        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        byte[] input = IOUtils.toByteArray(is);
        String image64 = Base64.encodeBase64String(input);
        DataTransferFile dataTransferFile = new DataTransferFile(requestedNodeName, nodeName,"image/jpeg", input);

        when(jcrDataAccess.post(dataTransferFile)).thenReturn("File added successfully ");

        String response = imageService.postImage64(nodeName, image64, "image/jpeg");

        verify(jcrDataAccess, times(1)).post(dataTransferFile);
        assertEquals("File added successfully ", response);

        IOUtils.closeQuietly(is);
    }

    @Test
    public void testGetImage64AndExpectedIsOk() throws IOException, JcrException {
        String nodeName = "testImageService103";
        String requestedNodeName = nodeName + IMAGE_SUFFIX;
        int height = 200;
        int width = 200;

        String fileNameFromResources = "test-files/input-1024x768.jpg";
        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        byte[] input = IOUtils.toByteArray(is);
        DataTransferFile dataTransferFile = new DataTransferFile(requestedNodeName, nodeName,"image/jpeg", input);

            BufferedImage img = ImageIO.read(new ByteArrayInputStream(input));
            BufferedImage scaledImg = Scalr.resize(img, Scalr.Mode.AUTOMATIC, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(scaledImg, "jpg", baos);
            byte[] imageInByte = baos.toByteArray();
            String image64 = Base64.encodeBase64String(imageInByte);
            baos.flush();
            baos.close();

        DataTransferFile scaledPic = new DataTransferFile(nodeName, nodeName,"image/jpeg", imageInByte);

        when(jcrDataAccess.get(requestedNodeName)).thenReturn(dataTransferFile);
        when(imageProcessor.resize(dataTransferFile, dataTransferFile.getMimeType(), width, height)).thenReturn(scaledPic);

        Base64DataTransferFile response64 = imageService.getImage64(nodeName, width, height);

        verify(jcrDataAccess, times(1)).get(requestedNodeName);
        verify(imageProcessor, times(1)).resize(dataTransferFile, dataTransferFile.getMimeType(), width, height);
        assertEquals("data:image/jpeg;base64," + image64, response64.getContent());
        assertEquals("image/jpeg", response64.getContentType());

        IOUtils.closeQuietly(is);
    }

    @Test
    public void testGetImage64InOriginalSizeAndExpectedIsOk() throws IOException, JcrException {
        String nodeName = "testImageService103";
        String requestedNodeName = nodeName + IMAGE_SUFFIX;

        String fileNameFromResources = "test-files/input-1024x768.jpg";
        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        byte[] input = IOUtils.toByteArray(is);
        String image64 = Base64.encodeBase64String(input);
        DataTransferFile dataTransferFile = new DataTransferFile(requestedNodeName, nodeName,"image/jpeg", input);

        when(jcrDataAccess.get(requestedNodeName)).thenReturn(dataTransferFile);

        Base64DataTransferFile response64 = imageService.getImage64(nodeName);

        verify(jcrDataAccess, times(1)).get(requestedNodeName);
        assertEquals("data:image/jpeg;base64," + image64, response64.getContent());
        assertEquals("image/jpeg", response64.getContentType());

        IOUtils.closeQuietly(is);
    }

    @Test(expected = NotExistingNodeJcrException.class)
    public void testGetImage64WithNonExistentNodeNameAndExpectedIsJcrException() throws JcrException, IOException {
        String nodeName = "testImageServiceNonExistent";
        String requestedNodeName = nodeName + IMAGE_SUFFIX;
        int height = 200;
        int width = 200;

        when(jcrDataAccess.get(requestedNodeName)).thenThrow(new NotExistingNodeJcrException());
        Base64DataTransferFile response64 = imageService.getImage64(nodeName, width, height);
        verify(jcrDataAccess, times(1)).get(requestedNodeName);
        assertNull(response64);
    }
}
