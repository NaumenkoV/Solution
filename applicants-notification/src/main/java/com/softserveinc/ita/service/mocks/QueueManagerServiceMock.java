package com.softserveinc.ita.service.mocks;

import com.softserveinc.ita.entity.NotificationJSONInfo;
import com.softserveinc.ita.service.MailService;
import com.softserveinc.ita.service.QueueManager;
import com.softserveinc.ita.service.exception.HttpRequestException;
import com.softserveinc.ita.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class QueueManagerServiceMock implements QueueManager {

    @Autowired
    private MailService mailService;

    @Autowired
    private JsonUtil jsonUtil;

    static Logger log = Logger.getLogger(
            QueueManagerServiceMock.class.getName());

    public void notifyApplicants(List<NotificationJSONInfo> infoList) throws HttpRequestException {
        for (NotificationJSONInfo info : infoList) {
            log.info("sending mock message " + info );
            mailService.notifyApplicant(jsonUtil.toJson(info));
        }
    }
}
