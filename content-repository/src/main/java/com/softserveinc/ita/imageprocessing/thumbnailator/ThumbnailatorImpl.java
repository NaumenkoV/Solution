package com.softserveinc.ita.imageprocessing.thumbnailator;

import com.softserveinc.ita.entity.DataTransferFile;
import com.softserveinc.ita.imageprocessing.AbstractImageProcessor;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Uses Thumbnailator library
 * https://code.google.com/p/thumbnailator/
 */
public class ThumbnailatorImpl extends AbstractImageProcessor {

    /**
     * Makes resize of image
     * @param source - ImageFile that need to be resized
     * @param mimeType - mime-type of image
     * @param height - required height
     * @param width - required width
     * @return - ImageFile with resized image (as byte array)
     * @throws java.io.IOException
     */
    @Override
    public DataTransferFile resize(DataTransferFile source, String mimeType, int width, int height) throws IOException {
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(source.getContent()));
        BufferedImage thumbnail = Thumbnails.of(img)
                .size(width, height)
                .asBufferedImage();

        byte[] imageInByte = writeImage(thumbnail, mimeType);

        return new DataTransferFile(source.getNodeName(), source.getOriginalFileName(), source.getMimeType(), imageInByte);
    }
}
