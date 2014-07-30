package com.softserveinc.ita.entity;

import java.io.Serializable;

public class Base64DataTransferFile implements Serializable{
    private String content;
    private String contentType;

    public Base64DataTransferFile() {
    }

    public Base64DataTransferFile(String content, String contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Base64DataTransferFile that = (Base64DataTransferFile) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Base64DataTransferFile{" +
                "content='" + content + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
