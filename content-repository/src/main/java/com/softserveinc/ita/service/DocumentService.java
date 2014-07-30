package com.softserveinc.ita.service;

import com.softserveinc.ita.entity.DataTransferFile;
import com.softserveinc.ita.exception.JcrException;

public interface DocumentService {
    String postDocument(DataTransferFile dataTransferFile) throws JcrException;
    DataTransferFile getDocument(String nodeName) throws JcrException;
    String deleteDocument(String nodeName) throws JcrException;
}
