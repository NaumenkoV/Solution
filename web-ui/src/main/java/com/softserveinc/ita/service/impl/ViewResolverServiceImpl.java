package com.softserveinc.ita.service.impl;

import com.softserveinc.ita.GroupStatusCalculator;
import com.softserveinc.ita.entity.Group;
import com.softserveinc.ita.service.HttpRequestExecutor;
import com.softserveinc.ita.service.ViewResolverService;
import com.softserveinc.ita.service.exception.HttpRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewResolverServiceImpl implements ViewResolverService {

    @Autowired
    private HttpRequestExecutor httpRequestExecutor;

    @Override
    public String choosePageByGroupId(String groupId, long currentTime) {
        try {
            Group group = httpRequestExecutor.getObjectByID(groupId, Group.class);
            Group.Status groupStatus = GroupStatusCalculator.calculateGroupStatus(group.getStartBoardingTime(),
                    group.getStartTime(), group.getEndTime(), currentTime);
            switch (groupStatus) {
                case PLANNED:
                    return "planned";
                case BOARDING:
                    return "boarding";
                case IN_PROCESS:
                    return "inprogress";
                case FINISHED:
                    return "404";
            }
        } catch (HttpRequestException e) {
            e.printStackTrace();
        }
        return "404";
    }
}
