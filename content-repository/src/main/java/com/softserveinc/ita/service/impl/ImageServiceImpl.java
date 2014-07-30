package com.softserveinc.ita.service.impl;

import com.softserveinc.ita.entity.Base64DataTransferFile;
import com.softserveinc.ita.entity.DataTransferFile;
import com.softserveinc.ita.exception.JcrException;
import com.softserveinc.ita.imageprocessing.ImageProcessor;
import com.softserveinc.ita.jcr.JcrDataAccess;
import com.softserveinc.ita.service.ImageService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ImageServiceImpl implements ImageService {
    private static final String IMAGE_SUFFIX = "-image";

    @Autowired
    private ImageProcessor imageProcessor;

    @Autowired
    private JcrDataAccess jcrDataAccess;

    private String getImageNodeName(String nodeName) {
        return nodeName + IMAGE_SUFFIX;
    }

    /**
     * Executes POST and PUT
     * @param dataTransferFile - image to save
     * @return - String with operation status
     * @throws com.softserveinc.ita.exception.JcrException
     */
    @Override
    public String postImage(DataTransferFile dataTransferFile) throws JcrException {
        String tempDataTransferFileName = getImageNodeName(dataTransferFile.getNodeName());
        dataTransferFile.setNodeName(tempDataTransferFileName);
        return jcrDataAccess.post(dataTransferFile);
    }

    /**
     * Returns Image in original size
     * @param nodeName - name of image (Node name - applicantID)
     * @return - ImageFile with original size pic
     * @throws com.softserveinc.ita.exception.JcrException
     */
    @Override
    public DataTransferFile getImage(String nodeName) throws JcrException {
        String tempNodeName = getImageNodeName(nodeName);
        return jcrDataAccess.get(tempNodeName);
    }

    /**
     * Processing Image resize function
     * @param nodeName - name of image (Node name - applicantID)
     * @param height - height of requested pic
     * @param width - width of requested pic
     * @return - ImageFile with image-processed pic
     * @throws com.softserveinc.ita.exception.JcrException
     * @throws java.io.IOException
     */
    @Override
    public DataTransferFile getImage(String nodeName, int width, int height) throws JcrException, IOException {
        String tempNodeName = getImageNodeName(nodeName);
        DataTransferFile tempImFile = jcrDataAccess.get(tempNodeName);
        return imageProcessor.resize(tempImFile, tempImFile.getMimeType(), width, height);
    }


    @Override
    public String deleteImage(String nodeName) throws JcrException {
        String tempNodeName = getImageNodeName(nodeName);
        return jcrDataAccess.delete(tempNodeName);
    }

    /**
     * Decoding Image and saves it in repository
     * @param nodeName - name of image (Node name - applicantID)
     * @param source - Base64 String with image
     * @param mime - real mime-type of image
     * @return - String with operation status
     * @throws com.softserveinc.ita.exception.JcrException
     */
    @Override
    public String postImage64(String nodeName, String source, String mime) throws JcrException {
        byte[] fromSource = Base64.decodeBase64(source);
        DataTransferFile tempFile = new DataTransferFile(nodeName, nodeName, mime, fromSource);

        return postImage(tempFile);
    }

    /**
     * Returns encoded image with original size
     * @param nodeName - name of image (Node name - applicantID)
     * @return - Base64 String with image
     * @throws com.softserveinc.ita.exception.JcrException
     */
    @Override
    public Base64DataTransferFile getImage64(String nodeName) throws JcrException {
        DataTransferFile tempFile = getImage(nodeName);
        String contentType = tempFile.getMimeType();

        //
        String contourChart = "data:" + contentType + ";base64," +
                StringUtils.newStringUtf8(Base64.encodeBase64(tempFile.getContent(), false));
        //

        //
//        String contourChart = StringUtils.newStringUtf8(Base64.encodeBase64(tempFile.getContent(), false)); //for pure image string
        //

        return new Base64DataTransferFile(contourChart, contentType);
    }

    /**
     * Encodes Image into Base64 String
     * @param nodeName - name of image (Node name - applicantID)
     * @param width - width of requested pic
     * @param height - height of requested pic
     * @return - Base64 String with Image
     * @throws com.softserveinc.ita.exception.JcrException
     * @throws java.io.IOException
     */
    @Override
    public Base64DataTransferFile getImage64(String nodeName, int width, int height) throws JcrException, IOException {
        DataTransferFile tempFile = getImage(nodeName, width, height);
        String contentType = tempFile.getMimeType();

        //
        String contourChart = "data:" + contentType + ";base64," +
                StringUtils.newStringUtf8(Base64.encodeBase64(tempFile.getContent(), false));
        //

        //
//        String contourChart = StringUtils.newStringUtf8(Base64.encodeBase64(tempFile.getContent(), false));
        //

        return new Base64DataTransferFile(contourChart, contentType);
    }
}
