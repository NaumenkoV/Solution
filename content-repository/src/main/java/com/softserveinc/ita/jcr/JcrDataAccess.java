package com.softserveinc.ita.jcr;

import com.softserveinc.ita.entity.DataTransferFile;
import com.softserveinc.ita.exception.JcrException;

public interface JcrDataAccess {
    String post(DataTransferFile dataTransferFile) throws JcrException;
    DataTransferFile get(String nodeName) throws JcrException;
    String delete(String nodeName) throws JcrException;
}
