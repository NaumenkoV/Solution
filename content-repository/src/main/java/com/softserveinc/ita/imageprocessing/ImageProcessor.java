package com.softserveinc.ita.imageprocessing;

import com.softserveinc.ita.entity.DataTransferFile;

import java.io.IOException;

public interface ImageProcessor {
    DataTransferFile resize(DataTransferFile source, String mimeType, int width, int height) throws IOException;
}
