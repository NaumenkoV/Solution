package com.softserveinc.ita.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Just simple entity to handle some data
 */
public class DataTransferFile implements Serializable{
    private String nodeName;
    private String originalFileName;
    private String mimeType;
    private byte[] content;

    public DataTransferFile() {
    }

    public DataTransferFile(String nodeName, String originalFileName, String mimeType, byte[] content) {
        this.nodeName = nodeName;
        this.originalFileName = originalFileName;
        this.mimeType = mimeType;
        this.content = content;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataTransferFile dataTransferFile = (DataTransferFile) o;

        if (nodeName != null ? !nodeName.equals(dataTransferFile.nodeName) : dataTransferFile.nodeName != null)
            return false;
        if (!Arrays.equals(content, dataTransferFile.content)) return false;
        if (mimeType != null ? !mimeType.equals(dataTransferFile.mimeType) : dataTransferFile.mimeType != null) return false;
        if (originalFileName != null ? !originalFileName.equals(dataTransferFile.originalFileName) : dataTransferFile.originalFileName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nodeName != null ? nodeName.hashCode() : 0;
        result = 31 * result + (originalFileName != null ? originalFileName.hashCode() : 0);
        result = 31 * result + (mimeType != null ? mimeType.hashCode() : 0);
        result = 31 * result + (content != null ? Arrays.hashCode(content) : 0);
        return result;
    }


}
