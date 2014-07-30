package com.softserveinc.ita.service.impl;

import com.softserveinc.ita.service.HttpRequestExecutor;
import com.softserveinc.ita.service.exception.HttpRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractHttpRequestExecutorRestImpl implements HttpRequestExecutor {

    private final int STATUS_CODE_OK = 200;
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public AbstractHttpRequestExecutorRestImpl(String baseUrl) {
        this.baseUrl = baseUrl;
        this.restTemplate  = new RestTemplate();
    }

        @Override
    public List<String> getAllObjectsID(Class objectClass) throws HttpRequestException {

        // add to URL path
        String subclass = objectClass.getSimpleName().toLowerCase();

        String urlReq = baseUrl + "/" + subclass + "s_id";

        return  getObject(urlReq, List.class);
    }

    @Override
    public List<String> getListObjectsIdByPrams(Class objectClass, Map<Class, String> urlValues) throws HttpRequestException {

        // name subdirectory
        String subclass = objectClass.getSimpleName().toLowerCase();


        StringBuilder urlPath =  new StringBuilder();
        // add to URL path
        urlPath.append(baseUrl).append("/").append(subclass).append("s?");

            Set<Class> keys =  urlValues.keySet();

            Iterator<Class>setItertor = keys.iterator();

                  while(setItertor.hasNext()){
                      Class clazz = setItertor.next();
                      String userId = urlValues.get(clazz);

                      urlPath.append(clazz.getSimpleName().toLowerCase()).append("=").append(urlValues.get(clazz));

                      if(setItertor.hasNext()){urlPath.append("&");}

        }



        return getObject(urlPath.toString(), List.class);


    }

    @Override
    public <T> T getObjectByID(String id, Class<T> objectClass) throws HttpRequestException {

        // name subdirectory
        String subclass = objectClass.getSimpleName().toLowerCase();

        // add to URL path
        String urlReq = baseUrl + "/" + subclass + "s/" + id;

        return getObject(urlReq, objectClass);

    }


    private <T> T getObject(String urlPath, Class<T> clazz) throws HttpRequestException {

        T entity = null;


        try {
            URI uri =  new URI(urlPath);
            entity = restTemplate.getForObject(uri, clazz);
        }catch (HttpServerErrorException httpServError) {
            throw new HttpRequestException(printException(httpServError), httpServError);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new HttpRequestException("Cant get URL in "+getClass().getSimpleName(),e);
        }
        return entity;
    }


    public RestTemplate getRestTemplate(){ return restTemplate;}

    public String getBaseUrl() {
        return baseUrl;
    }

    private String printException(HttpStatusCodeException ex){
      return   "\nStatus text : " + ex.getStatusText()+
              "\nStatus code : "  +ex.getStatusCode() +
              "\nResponse body : " +  ex.getResponseBodyAsString();
    }


}