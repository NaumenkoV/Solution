package com.softserveinc.ita.imageprocessing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class AbstractImageProcessor implements ImageProcessor{
    protected String imageExtension = "jpg";

    protected String detectMime(String mime) {

        if(mime.equals("image/gif")){
            return imageExtension = "gif";
        }
        if(mime.equals("image/png")){
            return imageExtension = "png";
        }
        if(mime.equals("image/bmp")){
            return imageExtension = "bmp";
        }
        return imageExtension;
    }

    protected byte[] writeImage(BufferedImage image, String mimeType) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(image, detectMime(mimeType), baos);
        byte[] imageInByte = baos.toByteArray();
        baos.flush();
        baos.close();

        return imageInByte;
    }
}
