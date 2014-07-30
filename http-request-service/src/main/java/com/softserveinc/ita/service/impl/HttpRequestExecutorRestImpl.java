package com.softserveinc.ita.service.impl;

import com.softserveinc.ita.service.HttpRequestExecutor;
import com.softserveinc.ita.service.exception.HttpRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class HttpRequestExecutorRestImpl extends AbstractHttpRequestExecutorRestImpl implements HttpRequestExecutor {


    public HttpRequestExecutorRestImpl(String baseUrl) {
        super(baseUrl);
    }




}