package com.softserveinc.ita.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Controller
@RequestMapping("/")
public class MainPageController {

	@RequestMapping(method = RequestMethod.GET)
         public String showMainPage() {
        return "main";
    }

    @RequestMapping(value = "sorry", method = RequestMethod.GET)
    public ModelAndView showSorryPage(@RequestParam("code") int code) {
        ModelAndView model = new ModelAndView();
        model.setViewName("errors/sorry");
        switch (code){
            case 1:
                model.addObject("error", "Can't find some cookies");
                model.addObject("description", "Unfortunately your cookies are not the ones that we expect. Check your browser settings");
                break;
            case 2:
                model.addObject("error", "Can't load Appointment");
                model.addObject("description", "Appointment service is unreachable for the moment, or you're asking for nonexistent appointment");
                break;
            case 3:
                model.addObject("error", "Can't load User/Applicant");
                model.addObject("description", "Either services are unreachable for the moment or you're asking for nonexistent data");
                break;
            default:
                model.addObject("error", "We don't know why you are here");
                model.addObject("description", "Probably there is no such error code");
                break;
        }
        return model;
    }

    //Spring Security see this :
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and/or password!");
        }
        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login/login");
        return model;
    }
}