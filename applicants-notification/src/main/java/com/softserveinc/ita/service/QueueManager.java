package com.softserveinc.ita.service;

import com.softserveinc.ita.entity.NotificationJSONInfo;
import com.softserveinc.ita.service.exception.HttpRequestException;

import java.util.List;

public interface QueueManager {
    public void notifyApplicants(List<NotificationJSONInfo> appointmentIDs) throws HttpRequestException;
}
