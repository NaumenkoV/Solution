package com.softserveinc.ita.service;

import com.softserveinc.ita.service.exception.HttpRequestException;

public interface MailService {
    public void notifyApplicant(String appointmentId) throws HttpRequestException;
}

