package com.softserveinc.ita.service;

import com.softserveinc.ita.service.exception.HttpRequestException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


public interface HttpRequestExecutor {


    List<String> getAllObjectsID(Class objectClass) throws HttpRequestException;


    List<String> getListObjectsIdByPrams(Class objectClass, Map<Class, String> urlValues) throws HttpRequestException;

    <T> T getObjectByID(String id, Class<T> objectClass) throws HttpRequestException;

    RestTemplate getRestTemplate();

    String getBaseUrl();
}
