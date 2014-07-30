var applicantsIDList = {};
var pageTemplate = {};
var applicants = [];
var groupCapacity;
var userList = [];
$(function () {
    groupID = getParamByName('groupID');
    $('nocontent').toggle();
    //load page template
    $.ajax({
        async: false,
        url: '/ui/group/mst/template.mst',
        type: "GET",
        success: function (data) {
            pageTemplate = data;
        },
        error: function () {
            console.log('Failed to load template from server!');
        }
    });
});
loadApplicantsIDListByStatus = function (status) {
    //load applicants list with status by group ID
    $.ajax({
        async: false,
        url: '/groups/' + groupID + '/applicants/',
        dataType: "json",
        type: "GET",
        data: 'status=' + status,
        success: function (data) {
            for (var property in data) {
                if (data.hasOwnProperty(property))
                    applicantsIDList[property] = data[property];
            }
        },
        error: function (data) {
            console.log("Failed to load applicants' data from server!");
        }
    });
};
loadApplicantsByStatus = function (status) {
    for (var property in applicantsIDList) {
        if (applicantsIDList.hasOwnProperty(property) && applicantsIDList[property]['status'] == status) {
            $.ajax({
                async: false,
                url: '/applicants/' + property,
                dataType: "json",
                type: "GET",
                success: function (data) {
                    var date = new Date(data.birthday);
                    data['birthday'] = date.getUTCMonth() + '/' + date.getUTCDate() + '/' + date.getUTCFullYear();
                    data['rank'] = applicantsIDList[property]['rank'];
                    data['status'] = status;
                    var appID = {};
                    var element = {};
                    switch (applicantsIDList[property]['status']) {
                        case 'NOT_SCHEDULED':
                            element = {applicant: data, appointment: {availableUsers: userList, scheduledUsers: []}};
                            break;
                        case 'SCHEDULED':
                            //load appointment id by groupid and applicant id
                            appID = loadAppointmentID(property);
                            element = {applicant: data, appointmentID: appID};
                            break;
                        case 'EMPLOYED':
                            data['employed'] = true;
                        case 'PASSED':
                        case 'NOT_PASSED':
                            data.photo = '/repository/img/applicant/' + property + '?height=200&width=150';
                            appID = loadAppointmentID(property);
                            element = {applicant: data, appointmentID: appID};
                            break;
                    }
                    applicants.push(element);
                }, error: function () {
                    console.log('content', "Failed to load applicant from server by id:" + property);
                }
            });
        }
    }
};
//load group capacity by groupID
loadGroupCapacity = function () {
    $.ajax({
        async: false,
        url: '/groups/capacity/' + groupID,
        dataType: "json",
        type: "GET",
        success: function (data) {
            groupCapacity = data;
        },
        error: function (data) {
            console.log("Failed to load group's data from server!");
        }
    });
};
//load users list
loadUsersList = function () {
    $.ajax({
        async: false,
        url: '/users',
        dataType: "json",
        type: "GET",
        success: function (data) {
            userList = data;
        },
        error: function (data) {
            console.log("Failed to load experts' data from server!");
        }
    });
};
submitList = function () {
    var list = {};
    var divlist = $('div.interviewed').children('div.container');
    $(divlist).each(function (index) {
        var applicant = $(this).children('div.applicant')[0];
        var applicantID = $(applicant).attr('applicantID');
        list[applicantID] = {};
        if (groupCapacity) {
            if (index < groupCapacity) {
                list[applicantID]['status'] = 'PASSED';
            } else {
                list[applicantID]['status'] = 'NOT_PASSED';
            }
        } else {
            list[applicantID]['status'] =
                elementByApplicantID(applicantID)['applicant']['status'];
        }
        list[applicantID]['rank'] = index;
    });
    //update statuses at server
    $.ajax({
        async: false,
        url: '/groups/' + groupID + '/applicants',
        dataType: "json",
        contentType: "application/json",
        type: "PUT",
        data: JSON.stringify(list),
        success: function () {
            applicants.forEach(function (element) {
                element['applicant']['status'] = list[element.applicant.id];
            });
            $("#dialog").data('content', "Saved!");
            $('#dialog').dialog('open');
        },
        error: function (data) {
            $("#dialog").data('content', "Failed to upload applicants' data to server!");
            $('#dialog').dialog('open');
        }
    });
};
setEventListeners = function () {
    $('.interview').click(function (event) {
        beginInterview(event.target);
    });
//    $("input[type=file]").nicefileinput();
    //Event listener for dialog
    $("#dialog").dialog({
        buttons: {
            "Close": function () {
                $(this).dialog("close");
            }
        },
        autoOpen: false,
        modal: true,
        open: function () {
            var p = $(this).children("p");
            $(p).text($(this).data().content);
        },
        show: { effect: "fade", duration: 800 },
        hide: { effect: "fade", duration: 800 }
    });

    $(".removeUser").click(function (event) {
        removeUser(event);
    });
    var datePicker = $(".date");
    datePicker.datepicker();
    $("select").selectmenu();
    $("#radioset").buttonset();
    $("button").button();
    $("input.validatable").change(function (event) {
        validateInput(event.target);
    });
    datePicker.change(function (event) {
        validateDate(event.target);
    });
    $("button.submit").button({disabled: true});
    $("#sort").click(function () {
        sort($('div.interviewed').children('div.container')[0], [], true);
    });
    $("#submitList").click(function () {
        submitList();
    });
    $(".editApplicant").click(function (event) {
        var editable = $(event.target).closest('div').find('.editable');
        $(editable).removeAttr('disabled');
        var submitButton = $(event.target).closest('div').find('button.editable');
        $(submitButton).button({disabled: false});
        $(event.target).closest('button').button({disabled: true});
    });
    $(".editAppointment").click(function (event) {
        var parentdiv = $(event.target).closest('div.schedule');
        var editable = parentdiv.find('input.schedulable');
        $(editable).removeAttr('disabled');
        parentdiv.find('button.removeUser').button({disabled: false});
        if (parentdiv.find('select.users').children('option:enabled').length) {
            parentdiv.find('button.addUser').button({disabled: false});
        }
        parentdiv.find('button.postAppointment').button({disabled: false});
        $(event.target).parent().button({disabled: true});
    });
    $(".addUser").click(function (event) {
        addUser(event);
    });
    $(".postAppointment").click(function (event) {
        postAppointment(event);
    });
    $('.submit, .add').click(function (event) {
        submitApplicant(event);
    });
    $('a').button();
    $('.notify').click(function (event) {
        notifyApplicant(event.target);
    });
    $('#notifications').click(function (event) {
        notifyListOfApplicants(event.target);
    });
    $('.employ').click(function (event) {
        employApplicant(event.target);
    });
};
notifyListOfApplicants = function (target) {
    var list = [];
    var hrid = getHRID();
    $(target).closest('div.applicant').siblings('div.applicant').addBack().each(function (index, element) {
        list.push({applicantId: $(element).attr('applicantID'),
            groupId: groupID,
            responsibleHrId: hrid
        });
    });
    notify(list);
};
notifyApplicant = function (target) {
    var appid = $(target).closest('div.applicant').attr('applicantID');
    notify([
        {applicantId: appid,
            groupId: groupID,
            responsibleHrId: getHRID()
        }
    ]);
};
postRender = function () {
    $(".accordion").accordion({heightStyle: "content", "collapsible": true, active: false, header: ".accordion-section"});
    setEventListeners();
};
sort = function (item, apps, light) {
    var sortableList = $('div.interviewed');
    var listitems = $('div.container', sortableList);
    listitems.sort(function (a, b) {
        var appIDa = $(a).children('div.applicant').attr('applicantid');
        var appIDb = $(b).children('div.applicant').attr('applicantid');
        var indexOfAppA = elementByApplicantID(appIDa)['applicant']['rank'];
        var indexOfAppB = elementByApplicantID(appIDb)['applicant']['rank'];
        if (indexOfAppA != -1 && indexOfAppB != -1) {
            return indexOfAppA > indexOfAppB;
        }
        if (indexOfAppA == -1 && indexOfAppB == -1) {
            return $(a).children("h3").children("span.points").text()
                < $(b).children("h3").children("span.points").text();
        }
        return indexOfAppA != -1;
    });
    sortableList.append(listitems);
    if (light) {
        highlight(item);
    }
};
elementByApplicantID = function (value) {
    for (var i = 0; i < applicants.length; i += 1) {
        if (applicants[i]['applicant']['id'] == value) {
            return applicants[i];
        }
    }
    return -1;
};
loadAppointmentID = function (applicantID) {
    var result = -1;
    $.ajax({
        async: false,
        url: '/appointments?group=' + groupID + '&applicant=' + applicantID,
        dataType: "text",
        type: "GET",
        success: function (appointmentID) {
            result = appointmentID;
        }
    });
    return result;
};
notify = function (object) {
    $.ajax({
        async: false,
        url: '/notification',
        contentType: "application/json",
        dataType: "application/json",
        type: 'POST',
        data: JSON.stringify(object),
        success: function () {
            $("#dialog").data('content', "Applicant(s) will be notified in a while");
            $('#dialog').dialog('open');
        },
        error: function () {
            $("#dialog").data('content', "Failed to notify applicant(s)!");
            $('#dialog').dialog('open');
        }
    });
};
getCookie = function (cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1);
        if (c.indexOf(name) != -1) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
};
getHRID = function () {
    return getCookie("userId");
};
getParamByName = function (name) {
    if (name = (new RegExp('[?&]' + encodeURIComponent(name) + '=([^&]*)')).exec(location.search))
        return decodeURIComponent(name[1]);
};