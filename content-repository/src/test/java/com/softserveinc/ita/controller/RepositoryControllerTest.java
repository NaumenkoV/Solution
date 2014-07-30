package com.softserveinc.ita.controller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class RepositoryControllerTest extends BaseControllerTest {
    private static final String IMAGE_SUFFIX = "-image";
    private static final String DOCUMENT_SUFFIX = "-document";
    private static final String APPLICANT_SUFFIX = "-applicant";
    private static final String USER_SUFFIX = "-user";

    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;
    @Before
    public void setup() {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void testPOSTUserImageAndExpectedIsOk() throws Exception {
        String name = "testController100";
        String endpoint = "/imgfile/user/" + name;
        String fileNameFromResources = "test-files/input-1024x768.jpg";

        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test-files/input-1024x768.jpg", "image/jpeg", is);

        mockMvc.perform(fileUpload(endpoint)
                .file(multipartFile))
                .andExpect(content().string("{\"status\":\"File added successfully " + name + USER_SUFFIX + IMAGE_SUFFIX + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPOSTApplicantImageAndExpectedIsOk() throws Exception {
        String name = "testController100";
        String endpoint = "/imgfile/applicant/" + name;
        String fileNameFromResources = "test-files/input-1024x768.jpg";

        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test-files/input-1024x768.jpg", "image/jpeg", is);

        mockMvc.perform(fileUpload(endpoint)
                .file(multipartFile))
                .andExpect(content().string("{\"status\":\"File added successfully " + name + APPLICANT_SUFFIX + IMAGE_SUFFIX + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPOSTUserImageAndExpectedIsException() throws Exception {
        String name = "testController101";
        String endpoint = "/imgfile/user/" + name;

        MockMultipartFile multipartFile = new MockMultipartFile("file", "input.txt", "text/plain", "Hello World".getBytes());

        mockMvc.perform(fileUpload(endpoint)
                .file(multipartFile))
                .andExpect(content().string("{\"reason\":\"You failed to upload because the file has incorrect type: "
                                                + name + ". Caused by: " + multipartFile.getContentType() + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPOSTApplicantImageAndExpectedIsException() throws Exception {
        String name = "testController101";
        String endpoint = "/imgfile/applicant/" + name;

        MockMultipartFile multipartFile = new MockMultipartFile("file", "input.txt", "text/plain", "Hello World".getBytes());

        mockMvc.perform(fileUpload(endpoint)
                .file(multipartFile))
                .andExpect(content().string("{\"reason\":\"You failed to upload because the file has incorrect type: "
                                                + name + ". Caused by: " + multipartFile.getContentType() + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPOSTUFOImageAndExpectedIsException() throws Exception {
        String name = "testController101";
        String ufo = "alf";
        String endpoint = "/imgfile/" + ufo + "/" + name;

        MockMultipartFile multipartFile = new MockMultipartFile("file", "iWantToBelieve.png", "image/png", "Hello World".getBytes());

        mockMvc.perform(fileUpload(endpoint)
                .file(multipartFile))
                .andExpect(content().string("{\"reason\":\"You failed to upload because of invalid path: must contain"
                                            + " %applicant% or %user% in it. Caused by: " + ufo + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGETUserImageAndExpectedIsOk() throws Exception {
        String name = "testController102";
        String endpointPost = "/imgfile/user/" + name;
        String endpointGet = "/imgfile/user/" + name + "?height=200&width=200";
        String fileNameFromResources = "test-files/input-1024x768.jpg";

        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test-files/input-1024x768.jpg", "image/jpeg", is);

        BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("/" + fileNameFromResources));
        BufferedImage scaledImg = Scalr.resize(img, Scalr.Mode.AUTOMATIC, 200, 200);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(scaledImg, "jpg", baos);
        byte[] imageInByte = baos.toByteArray();
        baos.flush();
        baos.close();


        mockMvc.perform(fileUpload(endpointPost)
                .file(multipartFile))
                .andExpect(status().isOk());

        mockMvc.perform(get(endpointGet))
                .andExpect(content().bytes(imageInByte))
                .andExpect(content().contentType("image/jpeg"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGETApplicantImageAndExpectedIsOk() throws Exception {
        String name = "testController102";
        String endpointPost = "/imgfile/applicant/" + name;
        String endpointGet = "/imgfile/applicant/" + name + "?height=200&width=200";
        String fileNameFromResources = "test-files/input-1024x768.jpg";

        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test-files/input-1024x768.jpg", "image/jpeg", is);

        BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("/" + fileNameFromResources));
        BufferedImage scaledImg = Scalr.resize(img, Scalr.Mode.AUTOMATIC, 200, 200);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(scaledImg, "jpg", baos);
        byte[] imageInByte = baos.toByteArray();
        baos.flush();
        baos.close();


        mockMvc.perform(fileUpload(endpointPost)
                .file(multipartFile))
                .andExpect(status().isOk());

        mockMvc.perform(get(endpointGet))
                .andExpect(content().bytes(imageInByte))
                .andExpect(content().contentType("image/jpeg"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGETUFOImageAndExpectedIsException() throws Exception {
        String name = "testController102";
        String ufo = "ufo";
        String endpointGet = "/imgfile/" + ufo + "/" + name + "?height=200&width=200";

        mockMvc.perform(get(endpointGet))
                .andExpect(content().string("{\"reason\":\"You failed to upload because of invalid path: must contain"
                        + " %applicant% or %user% in it. Caused by: " + ufo + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGETApplicantImageAndExpectedIsError() throws Exception {
        String name = "testControllerNonExistent";
        String endpointGet = "/imgfile/applicant/" + name + "?height=200&width=200";

        mockMvc.perform(get(endpointGet))
                .andExpect(content().string("{\"reason\":\"Bad request - Such node doesn't exist: "
                                                + name + APPLICANT_SUFFIX + IMAGE_SUFFIX + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteApplicantImageAndExpectedIsOk() throws Exception {
        String name = "testController103";
        String endpoint = "/imgfile/applicant/" + name;
        String fileNameFromResources = "test-files/input-1024x768.jpg";

        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test-files/input-1024x768.jpg", "image/jpeg", is);

        mockMvc.perform(fileUpload(endpoint)
                .file(multipartFile))
                .andExpect(status().isOk());

        mockMvc.perform(delete(endpoint))
                .andExpect(content().string("{\"status\":\"File successfully deleted " + name + APPLICANT_SUFFIX
                                                + IMAGE_SUFFIX + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUserImageAndExpectedIsOk() throws Exception {
        String name = "testController103";
        String endpoint = "/imgfile/user/" + name;
        String fileNameFromResources = "test-files/input-1024x768.jpg";

        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test-files/input-1024x768.jpg", "image/jpeg", is);

        mockMvc.perform(fileUpload(endpoint)
                .file(multipartFile))
                .andExpect(status().isOk());

        mockMvc.perform(delete(endpoint))
                .andExpect(content().string("{\"status\":\"File successfully deleted " + name + USER_SUFFIX
                                                + IMAGE_SUFFIX + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUFOImageAndExpectedIsException() throws Exception {
        String name = "testController103";
        String ufo = "ufo";
        String endpoint = "/imgfile/" + ufo + "/" + name;

        mockMvc.perform(delete(endpoint))
                .andExpect(content().string("{\"reason\":\"You failed to upload because of invalid path: must contain"
                                                + " %applicant% or %user% in it. Caused by: " + ufo + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteApplicantImageAndExpectedIsError() throws Exception {
        String name = "testController104";
        String endpoint = "/imgfile/applicant/" + name;

        mockMvc.perform(delete(endpoint))
                .andExpect(content().string("{\"reason\":\"Bad request - Such node doesn't exist: "
                                               + name + APPLICANT_SUFFIX + IMAGE_SUFFIX + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGETApplicantImageOriginalsizeAndExpectedIsOk() throws Exception {
        String name = "testController105";
        String endpointPost = "/imgfile/applicant/" + name;
        String endpointGet = "/imgfile/applicant/" + name;
        String fileNameFromResources = "test-files/input-1024x768.jpg";

        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test-files/input-1024x768.jpg", "image/jpeg", is);

        byte[] imageInByte = IOUtils.toByteArray(this.getClass()
                .getResourceAsStream("/" + fileNameFromResources));

        mockMvc.perform(fileUpload(endpointPost)
                .file(multipartFile))
                .andExpect(status().isOk());

        mockMvc.perform(get(endpointGet))
                .andExpect(content().bytes(imageInByte))
                .andExpect(content().contentType("image/jpeg"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestPostUserImage64AndExpectedIsOk() throws Exception {
        String name = "testController105";
        String fileNameFromResources = "test-files/input-1024x768.jpg";
        String endpointPost = "/img/user/" + name;

        byte[] imageInByte = IOUtils.toByteArray(this.getClass()
                .getResourceAsStream("/" + fileNameFromResources));

        String imageInString = Base64.encodeBase64String(imageInByte);

        mockMvc.perform(post(endpointPost)
                .content("data:image/jpeg;base64," + imageInString)
                .header("Content-Type", "image/jpeg"))
                .andExpect(content().string("{\"status\":\"File added successfully " + name + USER_SUFFIX
                        + IMAGE_SUFFIX + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestPostApplicantImage64AndExpectedIsOk() throws Exception {
        String name = "testController105";
        String fileNameFromResources = "test-files/input-1024x768.jpg";
        String endpointPost = "/img/applicant/" + name;

        byte[] imageInByte = IOUtils.toByteArray(this.getClass()
                .getResourceAsStream("/" + fileNameFromResources));

        String imageInString = Base64.encodeBase64String(imageInByte);

        mockMvc.perform(post(endpointPost)
                .content("data:image/jpeg;base64," + imageInString)
                .header("Content-Type", "image/jpeg"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestPostUfoImage64AndExpectedIsException() throws Exception {
        String name = "testController105";
        String ufo = "ufo";
        String fileNameFromResources = "test-files/input-1024x768.jpg";
        String endpointPost = "/img/" + ufo + "/" + name;

        byte[] imageInByte = IOUtils.toByteArray(this.getClass()
                .getResourceAsStream("/" + fileNameFromResources));

        String imageInString = Base64.encodeBase64String(imageInByte);

        mockMvc.perform(post(endpointPost)
                .content("data:image/jpeg;base64," + imageInString)
                .header("Content-Type", "image/jpeg"))
                .andExpect(content().string("{\"reason\":\"You failed to upload because of invalid path: must contain"
                                                     + " %applicant% or %user% in it. Caused by: " + ufo + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetApplicantImage64AndExpectedIsOk() throws Exception {
        String name = "testController106";
        String fileNameFromResources = "test-files/input-1024x768.jpg";
        String endpointPost = "/img/applicant/" + name;
        String endpointGet = "/img/applicant/" + name + "?height=200&width=200";

        byte[] imageInByte = IOUtils.toByteArray(this.getClass()
                .getResourceAsStream("/" + fileNameFromResources));

        String imageInString = Base64.encodeBase64String(imageInByte);

        mockMvc.perform(post(endpointPost)
                .content("data:image/jpeg;base64," + imageInString)
                .header("Content-Type", "image/jpeg"))
                .andExpect(status().isOk());


        BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("/" + fileNameFromResources));
        BufferedImage scaledImg = Scalr.resize(img, Scalr.Mode.AUTOMATIC, 200, 200);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(scaledImg, "jpg", baos);
        byte[] imageAfter = baos.toByteArray();
        baos.flush();
        baos.close();
        String image64After = Base64.encodeBase64String(imageAfter);


        mockMvc.perform(get(endpointGet))
                .andExpect(status().isOk())
                .andExpect(content().string("data:image/jpeg;base64," + image64After));
    }

    @Test
    public void testGetUserImage64OriginalSizeAndExpectedIsOk() throws Exception {
        String name = "testController107";
        String fileNameFromResources = "test-files/time-square-imgscarl-resize-QUALITY-AUTO-10x10.png";
        String endpointPost = "/img/user/" + name;
        String endpointGet = "/img/user/" + name;

        byte[] imageInByte = IOUtils.toByteArray(this.getClass()
                .getResourceAsStream("/" + fileNameFromResources));

        String imageInString = Base64.encodeBase64String(imageInByte);

        mockMvc.perform(post(endpointPost)
                .content("data:image/jpeg;base64," + imageInString)
                .header("Content-Type", "image/png"))
                .andExpect(status().isOk());


        mockMvc.perform(get(endpointGet))
                .andExpect(status().isOk())
                .andExpect(content().string("data:image/jpeg;base64," + imageInString));
    }

    @Test
    public void testGetUfoImage64OriginalSizeAndExpectedIsOk() throws Exception {
        String name = "testController107";
        String ufo = "ufo";
        String fileNameFromResources = "test-files/time-square-imgscarl-resize-QUALITY-AUTO-10x10.png";
        String endpointPost = "/img/user/" + name;
        String endpointGet = "/img/" + ufo + "/" + name;

        byte[] imageInByte = IOUtils.toByteArray(this.getClass()
                .getResourceAsStream("/" + fileNameFromResources));

        String imageInString = Base64.encodeBase64String(imageInByte);

        mockMvc.perform(post(endpointPost)
                .content("data:image/jpeg;base64," + imageInString)
                .header("Content-Type", "image/png"))
                .andExpect(status().isOk());


        mockMvc.perform(get(endpointGet))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"reason\":\"You failed to upload because of invalid path: must contain"
                                                  + " %applicant% or %user% in it. Caused by: " + ufo + "\"}"));
    }

    @Test
    public void testGetUserImage64AndExpectedIsJcrException() throws Exception {
        String name = "testControllerNonExistent";
        String endpointGet = "/img/user/" + name + "?height=200&width=200";

        mockMvc.perform(get(endpointGet))
                .andExpect(content().string("{\"reason\":\"Bad request - Such node doesn't exist: " + name
                                                    + USER_SUFFIX + IMAGE_SUFFIX + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetApplicantImage64OriginalSizeAndExpectedIsJcrException() throws Exception {
        String name = "testControllerNonExistent";
        String endpointGet = "/img/applicant/" + name;

        mockMvc.perform(get(endpointGet))
                .andExpect(content().string("{\"reason\":\"Bad request - Such node doesn't exist: "
                                                + name + APPLICANT_SUFFIX + IMAGE_SUFFIX + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPOSTDocumentAndExpectedIsOk() throws Exception {
        String name = "testController108";
        String endpoint = "/doc/" + name;
        String fileNameFromResources = "test-files/plain_text.txt";

        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        MockMultipartFile multipartFile = new MockMultipartFile("file", fileNameFromResources, "text/plain", is);

        mockMvc.perform(fileUpload(endpoint)
                .file(multipartFile))
                .andExpect(content().string("{\"status\":\"File added successfully " + name + APPLICANT_SUFFIX
                                                + DOCUMENT_SUFFIX + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPOSTDocumentAndExpectedIsError() throws Exception {
        String name = "testController109";
        String endpoint = "/doc/" + name;
        String fileNameFromResources = "test-files/plain_text.txt";

        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        MockMultipartFile multipartFile = new MockMultipartFile("file", fileNameFromResources, "image/png", is);

        mockMvc.perform(fileUpload(endpoint)
                .file(multipartFile))
                .andExpect(content().string("{\"reason\":\"You failed to upload because the file has incorrect type: "
                                                    + name + ". Caused by: image/png" + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGETDocumentAndExpectedIsOk() throws Exception {
        String name = "testController110";
        String endpoint= "/doc/" + name;
        String fileNameFromResources = "test-files/plain_text.txt";

        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        MockMultipartFile multipartFile = new MockMultipartFile("file", fileNameFromResources, "text/plain", is);

        byte[] docInByte = IOUtils.toByteArray(this.getClass().getResourceAsStream("/" + fileNameFromResources));

        mockMvc.perform(fileUpload(endpoint)
                .file(multipartFile))
                .andExpect(status().isOk());

        mockMvc.perform(get(endpoint))
                .andExpect(content().bytes(docInByte))
                .andExpect(content().contentType("text/plain"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetIDocumentAndExpectedIsJcrException() throws Exception {
        String name = "testControllerNonExistent";
        String endpointGet = "/doc/" + name;

        mockMvc.perform(get(endpointGet))
                .andExpect(content().string("{\"reason\":\"Bad request - Such node doesn't exist: " + name
                                                + APPLICANT_SUFFIX + DOCUMENT_SUFFIX + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteDocumentNodeAndExpectedIsOk() throws Exception {
        String name = "testController111";
        String endpoint = "/doc/" + name;
        String fileNameFromResources = "test-files/plain_text.txt";

        InputStream is = this.getClass().getResourceAsStream("/" + fileNameFromResources);
        MockMultipartFile multipartFile = new MockMultipartFile("file", fileNameFromResources, "text/plain", is);

        mockMvc.perform(fileUpload(endpoint)
                .file(multipartFile))
                .andExpect(status().isOk());

        mockMvc.perform(delete(endpoint))
                .andExpect(content().string("{\"status\":\"File successfully deleted " + name  + APPLICANT_SUFFIX
                                                    + DOCUMENT_SUFFIX + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteDocumentAndExpectedIsError() throws Exception {
        String name = "testControllerNonExistent";
        String endpoint = "/doc/" + name;

        mockMvc.perform(delete(endpoint))
                .andExpect(content().string("{\"reason\":\"Bad request - Such node doesn't exist: " + name
                                                + APPLICANT_SUFFIX + DOCUMENT_SUFFIX + "\"}"))
                .andExpect(status().isBadRequest());
    }
}
