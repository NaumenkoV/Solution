package com.softserveinc.ita.error;

import com.softserveinc.ita.entity.exceptions.ExceptionJSONInfo;
import com.softserveinc.ita.utils.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class RestResponseEntityExceptionHandlerTest extends BaseErrorTest {

    private MockMvc mockMvc;

    @Autowired
    private JsonUtil jsonUtil;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testEntityExceptionHandler() throws Exception {
          mockMvc.perform(
                  get("/entityException").accept(MediaType.APPLICATION_JSON))
                  .andExpect(content().string(jsonUtil.toJson(new ExceptionJSONInfo("Entity Exception. Failed dependency Entity exception"))));
    }
    @Test
    public void testGeneralExceptionHandler() throws Exception {
        mockMvc.perform(
                get("/generalException").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(jsonUtil.toJson(new ExceptionJSONInfo("Internal Server Error Exception"))));
    }


}
