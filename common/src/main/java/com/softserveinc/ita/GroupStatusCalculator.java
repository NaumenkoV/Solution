package com.softserveinc.ita;

import com.softserveinc.ita.entity.Group;

public class GroupStatusCalculator {

    public static Group.Status calculateGroupStatus(long boardingDate, long startTime, long endTime, long currentTime) {
        if (currentTime < boardingDate) {
            return Group.Status.PLANNED;
        }
        if (currentTime >= boardingDate && currentTime < startTime) {
            return Group.Status.BOARDING;
        }
        if (currentTime >= startTime && currentTime < endTime) {
            return Group.Status.IN_PROCESS;
        }
        return Group.Status.FINISHED;
    }
}
