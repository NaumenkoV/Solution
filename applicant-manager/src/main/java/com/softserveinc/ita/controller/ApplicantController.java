package com.softserveinc.ita.controller;

import com.softserveinc.ita.entity.Applicant;
import com.softserveinc.ita.exception.ApplicantDoesNotExistException;
import com.softserveinc.ita.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Applicant>> getApplicants() {
        List<Applicant> resultList = applicantService.getApplicants();
        if(resultList.isEmpty() || resultList == null){
            return new ResponseEntity<>(resultList, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @RequestMapping(value = "{applicantId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Applicant getApplicantById(@PathVariable String applicantId)
            throws ApplicantDoesNotExistException {
        Applicant searchedApplicant = applicantService.getApplicantById(applicantId);
        return searchedApplicant;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Applicant> addNewApplicant(@RequestBody Applicant applicant) {
        Applicant newApplicant = applicantService.addNewApplicant(applicant);
        return new ResponseEntity<>(newApplicant, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Applicant> editApplicant(@RequestBody Applicant applicant) {
        Applicant editedApplicant = applicantService.editApplicant(applicant);
        return new ResponseEntity<>(editedApplicant, HttpStatus.OK);
    }

    @RequestMapping(value = "/applicantID", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<String>> getApplicantIDs() {
        List<String> response = applicantService.getApplicantIDList();
        if (response == null || response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
