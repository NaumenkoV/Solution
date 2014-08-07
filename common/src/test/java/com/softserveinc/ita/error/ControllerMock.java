package com.softserveinc.ita.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/")
@Controller
public class ControllerMock {
    @RequestMapping(value = "/entityException",method = RequestMethod.GET)
    public void throwEntityException() throws EntityException {
        throw new EntityException("Entity exception");
    }
    @RequestMapping(value = "/generalException",method = RequestMethod.GET)
    public void throwGenericException() throws Exception {
        throw new Exception("Exception");
    }
}
