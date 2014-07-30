package com.softserveinc.ita.servise.impl;
import com.softserveinc.ita.BaseHttpReqTest;
import com.softserveinc.ita.entity.Appointment;
import com.softserveinc.ita.entity.Group;
import com.softserveinc.ita.entity.User;
import com.softserveinc.ita.service.HttpRequestExecutor;
import com.softserveinc.ita.service.exception.HttpRequestException;
import com.softserveinc.ita.utils.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class HttpRequestExecutorTests extends BaseHttpReqTest {

    @Autowired
    private HttpRequestExecutor service;

    @Autowired
    private JsonUtil utilJson;

    private User user;

    private Appointment appointment;

    private Group group;

    private MockRestServiceServer mockServer;

    private List<String> listID;

    private  String urlSubclass;

    private String urlSecondParam;

    Map<Class,String> parametersMap;



    @Before
    public void setUp() {

         //Create  Mock Server;
        mockServer = MockRestServiceServer.createServer(service.getRestTemplate());



        appointment = new Appointment();
        appointment.setAppointmentId("1");

        user = new User("1");
        urlSubclass = user.getClass().getSimpleName().toLowerCase();

        listID = new ArrayList<>();
        listID.add("1");
        listID.add("2");
        listID.add("3");
        listID.add("4");
        listID.add("5");
        listID.add("6");

        parametersMap =  new LinkedHashMap<Class, String>();
        parametersMap.put(appointment.getClass(), appointment.getAppointmentId());
        parametersMap.put(user.getClass(),user.getId());

    }

     @Test
    public void testGetAllObjectsIDShouldRetrurnValidListID() throws Exception {

        mockServer.expect(requestTo(service.getBaseUrl()+"/"+ urlSubclass +"s_id"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(utilJson.toJson(listID), MediaType.APPLICATION_JSON));

        assertEquals(listID, service.getAllObjectsID(user.getClass()));
        mockServer.verify();
    }


    @Test
    public void testGetObjectByIDShouldReturnValidObject() throws Exception {
        mockServer.expect(requestTo(service.getBaseUrl()+"/"+ urlSubclass +"s/"+user.getId()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(utilJson.toJson(user),MediaType.APPLICATION_JSON));

        assertEquals(user, service.getObjectByID(user.getId(),user.getClass()) );
        mockServer.verify();

    }

    @Test (expected = HttpRequestException.class )
    public void testGetObjectByIdShouldReturnException() throws Exception {
        mockServer.expect(requestTo(service.getBaseUrl()+"/"+ urlSubclass +"s/"+user.getId()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        //noinspection unchecked
        service.getObjectByID(user.getId(), user.getClass());
        mockServer.verify();
    }



    @Test
    public void testGetListObjectsIdByPrams() throws Exception {
      List<String> expectedList = new LinkedList<String>();
        expectedList.add(user.getId());

        mockServer.expect(requestTo(service.getBaseUrl()+"/"+ urlSubclass +"s?"+
                appointment.getClass().getSimpleName().toLowerCase()+"=" +
                appointment.getAppointmentId()+"&"+ user.getClass().getSimpleName().toLowerCase()+"="+user.getId()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(utilJson.toJson(expectedList),MediaType.APPLICATION_JSON));


        assertEquals(expectedList, service.getListObjectsIdByPrams(user.getClass(),parametersMap) );
        mockServer.verify();
    }



}