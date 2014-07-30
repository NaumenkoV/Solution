package com.softserveinc.ita.service.mocks;

import com.softserveinc.ita.service.MailService;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class MailServiceMock implements MailService {
    static Logger logger = Logger.getLogger(
            MailServiceMock.class.getName());

    public void notifyApplicant(String info) {
        logger.info("Mock mail service. Info " + info + " was consumed");
    }
}
