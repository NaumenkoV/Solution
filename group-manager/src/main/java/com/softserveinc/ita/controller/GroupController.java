package com.softserveinc.ita.controller;

import com.softserveinc.ita.entity.Applicant;
import com.softserveinc.ita.entity.ApplicantBenchmark;
import com.softserveinc.ita.entity.Course;
import com.softserveinc.ita.entity.Group;
import com.softserveinc.ita.exception.impl.GroupDoesntExistException;
import com.softserveinc.ita.exception.impl.GroupWithThisNameIsAlreadyExists;
import com.softserveinc.ita.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/status/{status}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<Group> getGroupsByStatus(@PathVariable Group.Status status) {
        return groupService.getGroupsByStatus(status, System.currentTimeMillis());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Group getGroupById(@PathVariable String id) throws GroupDoesntExistException {
        return groupService.getGroupById(id);
    }

    @RequestMapping(value = "/courses", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<Course> getCourses() {
        return groupService.getCourses();
    }

    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    public
    @ResponseBody
    Group addGroup(@RequestBody Group group) throws GroupWithThisNameIsAlreadyExists {
        return groupService.createGroup(group);
    }

    @RequestMapping(value = "/allGroups", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @RequestMapping(value = "{groupId}", method = RequestMethod.DELETE, produces = "application/json")
    public
    @ResponseBody
    Boolean deleteGroupById(@PathVariable String groupId) throws GroupDoesntExistException {
        groupService.removeGroup(groupId);
        return true;
    }

    @RequestMapping(value = "/editGroup", method = RequestMethod.PUT, produces = "application/json")
    public
    @ResponseBody
    Group updateGroup(@RequestBody Group group) throws GroupWithThisNameIsAlreadyExists {
        return groupService.updateGroup(group);
    }

    @RequestMapping(value = "/capacity/{groupID}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    int getGroupCapacity(@PathVariable String groupID) {
        return groupService.getGroupCapacity(groupID);
    }

    @RequestMapping(value = "{groupID}/applicants", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Map<String, ApplicantBenchmark> getApplicantsByGroupIdAndStatus(@PathVariable String groupID,
                                                                    @RequestParam Applicant.Status status) {
        return groupService.getApplicantsByGroupIdAndStatus(groupID, status);
    }

    @RequestMapping(value = "{groupID}/applicants", method = RequestMethod.PUT, produces = "application/json")
    public
    @ResponseBody
    Group updateApplicantsInGroup(@PathVariable String groupID,
                                  @RequestBody Map<String, ApplicantBenchmark> applicants) {
        return groupService.updateApplicantsInGroup(groupID, applicants);
    }
}