package com.softserveinc.ita.image;

import com.softserveinc.ita.entity.DataTransferFile;
import com.softserveinc.ita.imageprocessing.ImageProcessor;
import com.softserveinc.ita.imageprocessing.thumbnailator.ThumbnailatorImpl;
import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Examples: https://code.google.com/p/thumbnailator/source/browse/test/net/coobird/thumbnailator/tasks/io/BufferedImageSourceTest.java?r=86bbeb6264b87f260af814cdf786e41bea8f7e53
 * Examples: https://code.google.com/p/thumbnailator/source/browse/test/net/coobird/thumbnailator/tasks/io/OutputStreamImageSinkTest.java?r=86bbeb6264b87f260af814cdf786e41bea8f7e53
 */
public class ThumbnailatorImplTest extends BaseImageScalTest {
    private ImageProcessor imageProcessor = new ThumbnailatorImpl();

    private  BufferedImage src; // was static
    private  byte[] bytesrc;
    private DataTransferFile source;

    private int height = 200;
    private int width = 200;

    @Before
    public void setup() throws IOException {
        src = load("test-files/time-square.png");
        bytesrc = loadByte("test-files/time-square.png");
        source = new DataTransferFile("nodeName", "originalName", "image/png", bytesrc);
    }

    @After
    public void tearDown() {
        src.flush();
    }

    protected BufferedImage load(String name) {
        BufferedImage i = null;

        try {
            i = ImageIO.read(this.getClass().getResourceAsStream("/" + name));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return i;
    }

    protected byte[] loadByte(String name) {
        byte[] input = new byte[0];

        try {
            input = IOUtils.toByteArray(this.getClass().getResourceAsStream("/" + name));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return input;
    }

    protected void assertEquals(BufferedImage orig, BufferedImage tmp) throws AssertionError {
        // Ensure neither image is null.
        Assert.assertNotNull(orig);
        Assert.assertNotNull(tmp);

        // Ensure dimensions are equal.
        Assert.assertEquals(orig.getWidth(), tmp.getWidth());
        Assert.assertEquals(orig.getHeight(), tmp.getHeight());
    }

    @Test
    public void testResizeWHExact() throws IOException {
        DataTransferFile tmpFile = imageProcessor.resize(source, source.getMimeType(), width, height);
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(tmpFile.getContent()));
        assertEquals(load("test-files/time-square-thumbnailator-200x200.png"), img);
    }
}