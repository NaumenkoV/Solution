package com.softserveinc.ita.controller;

import com.softserveinc.ita.entity.NotificationJSONInfo;
import com.softserveinc.ita.service.MailService;
import com.softserveinc.ita.service.QueueManager;
import com.softserveinc.ita.service.exception.HttpRequestException;
import com.softserveinc.ita.utils.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class ApplicantsNotificationController {
    static Logger log = Logger.getLogger(
            ApplicantsNotificationController.class.getName());
    @Autowired
    private QueueManager queueManagerService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody List<NotificationJSONInfo> postIDEntityListToNotifyApplicants(@RequestBody List<NotificationJSONInfo> infoList) throws HttpRequestException {
        queueManagerService.notifyApplicants(infoList);
        log.info("IDs: " + infoList + " was posted to the queue");
        return infoList;
    }
}
