//on document ready
$(function () {
    loadUsersList();
    loadApplicantsIDListByStatus('SCHEDULED');
    loadApplicantsIDListByStatus('NOT_SCHEDULED');
    loadApplicantsByStatus('SCHEDULED');
    loadApplicantsByStatus('NOT_SCHEDULED');
    //load appointments by id
    applicants.forEach(function (element) {
            if (element['applicant']['status'] == 'SCHEDULED') {
                $.ajax({
                    async: false,
                    url: '/appointments/' + element.appointmentID,
                    dataType: 'json',
                    type: "GET",
                    success: function (appointment) {
                        element['appointment'] = parseAppointment(appointment);
                    },
                    error: function () {
                        console.log('content', "Failed to load appointment from server by id:" + element.appointmentID);
                    }
                })
            }
        }
    );
    //render
    var emptyApplicant = {};
    //new applicant
    var newAppRendered = Mustache.render(pageTemplate, {'data': [emptyApplicant], 'newApp': true});
    $('.newapp').html(newAppRendered);
    //scheduled
    var scheduledRendered = Mustache.render(pageTemplate, {data: applicants.filter(function (element) {
        return element['applicant']['status'] == 'SCHEDULED';
    }), 'edit': true});
    $('.scheduled').html(scheduledRendered);
    //not scheduled
    var notScheduledRendered = Mustache.render(pageTemplate, {'data': applicants.filter(function (element) {
        return element['applicant']['status'] == 'NOT_SCHEDULED';
    }), 'schedule': true });
    $('.not_scheduled').html(notScheduledRendered);
    postRender();
    //enable button 'Begin interview' in case it should start in less than 30 mins
    checkInterviewAvailable();
    setInterval(checkInterviewAvailable(), 1000 * 30);
});
checkInterviewAvailable = function () {
    //check 'begin interview' button in each div
    $('div.schedule').each(function (index, element) {
        var date = $(element).find('.date')[0];
        var time = $(element).find('.time')[0];
        if (date && time) {
            //difference in current time and planned start time should be less than half an hour
            var planned = new Date($(date).val()).getTime() + parseTime($(time).val());
            if (planned - new Date().getTime() < 30 * 60 * 1000) {
                $(element).find('.interview').button({disabled: false});
            }
        }
    });
};
//when button 'begin interview' is clicked
beginInterview = function (target) {
    //write cookies and go to interview page
    var appointmentID = $(target).closest('div.schedule').attr('appointmentID');
    writeCookie('groupID', groupID);
    writeCookie('appointmentId', appointmentID);
    window.location.href = '/ui/interview';
};
//POST OR PUT an appointment
postAppointment = function (event) {
    var requestType = "POST";
    var dataType = "text";
    var id = "";
    //if appointment is edited, not newly created
    if ($(event.target).parent('button').hasClass("schedulable")) {
        requestType = "PUT";
        dataType = "json";
        //set appointment id, get it from div
        id = $(event.target).closest('div.schedule').attr('appointmentID');
    }
    var target = $(event.target);
    //div where the action happens
    var parentdiv = $(target).parent().closest("div.schedule");
    //experts scheduled to appointment
    var parent = parentdiv.find("select.scheduled");
    var names = [];
    $(parent).children("option").each(function () {
        names.push($(this).text())
    });
    //ids of experts scheduled to an appointment
    var userIDs = userIDByName(names);
    //date input
    var dateTarget = parentdiv.find(".date");
    //time input
    var timeTarget = parentdiv.find(".time");
    //duration input
    var durationTarget = parentdiv.find(".duration");
    //if all inputs are valid
    if (validateDate(dateTarget) && validateInput(parentdiv.find('input.validatable'))) {
        //if no users are scheduled
        if (userIDs.length == 0) {
            $("#dialog").data('content', 'Select at least one user!');
            $('#dialog').dialog('open');
        } else {
            //appointment object to send to server
            var appointment = {
                appointmentId: id,
                groupId: groupID,
                applicantId: $(event.target).closest('div.applicant').attr('applicantid'),
                userIdList: userIDs,
                durationTime: +$(durationTarget).val() * 60 * 1000,
                startTime: new Date($(dateTarget).val()).getTime() + parseTime($(timeTarget).val())};
            $.ajax({
                    async: false,
                    url: '/appointments/',
                    contentType: "application/json",
                    dataType: dataType,
                    data: JSON.stringify(appointment),
                    type: requestType,
                    success: function (data) {
                        //remove validation highlight
                        var inputs = $(event.target).closest('div.schedule').find('input');
                        inputs.removeClass('ui-state-highlight');
                        inputs.removeClass('ui-state-error');
                        if (requestType == 'PUT') {
                            //if appointment was edited and successfuly sent, disable editable inputs
                            disableElements(event.target);
                            $("#dialog").data('content', 'Appointment successfuly scheduled/edited');
                            $('#dialog').dialog('open');
                        } else {
                            //if a new appointment was created
                            //set appointment id
                            appointment['appointmentId'] = data;
                            //and create appointment in memory
                            createAppointment(appointment);
                        }
                    },
                    error: function () {
                        $("#dialog").data('content', 'Failed to schedule/edit appointment!');
                        $('#dialog').dialog('open');
                    }
                }
            );
        }
    }
};
disableElements = function (item) {
    //appointment parent div
    var scheduleParentDiv = $(item).closest('div.schedule');
    //applicant parent div
    var infoParentDiv = $(item).closest('div.info');
    if (scheduleParentDiv) {
        $(scheduleParentDiv).find("input.schedulable").prop('disabled', true);
        $(scheduleParentDiv).find('button.schedulable').button({disabled: true});
        $(scheduleParentDiv).find('.editAppointment').button({disabled: false});
    }
    if (infoParentDiv) {
        $(infoParentDiv).find('input').prop('disabled', true);
    }
};
//create in-memory appointment
createAppointment = function (newApointment) {
    //object to send to server. needed to update status and/or rank of applicant in group
    var tosend = {};
    var applicantID = newApointment['applicantId'];
    tosend[applicantID] = {};
    tosend[applicantID]['status'] = 'SCHEDULED';
    tosend[applicantID]['rank'] = -1;
    $.ajax({
        async: false,
        url: '/groups/' + groupID + '/applicants',
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(tosend),
        type: 'PUT',
        success: function () {
            //if status was successfully updated at server
            //create appointment in memory and set actual status
            var elementToChange = elementByApplicantID(applicantID);
            elementToChange['applicant']['status'] = 'SCHEDULED';
            elementToChange['appointmentID'] = newApointment['appointmentId'];
            elementToChange['appointment'] = parseAppointment(newApointment);
            //and rerender accordion
            var scheduledRendered = Mustache.render(pageTemplate, {'data': applicants.filter(function (element) {
                return element['applicant']['status'] == 'SCHEDULED';
            }), 'edit': true});
            $('.scheduled').html(scheduledRendered);
            var notScheduledRendered = Mustache.render(pageTemplate, {'data': applicants.filter(function (element) {
                return element['applicant']['status'] == 'NOT_SCHEDULED';
            }), 'schedule': true});
            $('.not_scheduled').html(notScheduledRendered);
            $(".accordion").accordion("destroy");
            postRender();
            $("#dialog").data('content', 'Information updated!');
            $('#dialog').dialog('open');
        },
        error: function () {
            $("#dialog").data('content', 'Failed to assign status to the applicant within a group!');
            $('#dialog').dialog('open');
        }
    });
};
//POST OR PUT applicant
submitApplicant = function (event) {
    //if new applicant was created
    var requestType = 'POST';
    var parentdiv = $(event.target).closest('div.info');
    var inputData = parentdiv.find('input.validatable');
    var inputDate = parentdiv.find('input.date');
    //if date and other info validation was successful
    if (validateInput(inputData) && validateDate(inputDate)) {
        //create object to send to server from input data
        var applicant = buildApplicant(inputData);
        //parse birthday
        applicant['birthday'] = new Date($(inputDate).val()).getTime();
        //new applicant, no id
        applicant['id'] = "";
        var applicantID = $(event.target).closest('div.applicant').attr('applicantID');
        if (applicantID) {
            //if appid !=null, than existing applicant was edited
            requestType = 'PUT';
            applicant['id'] = applicantID;
        }
        $.ajax({
            async: false,
            url: '/applicants/',
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(applicant),
            type: requestType,
            success: function (newApp) {
                if (requestType == 'PUT') {
                    disableElements(event.target);
                    //update applicant in memory
                    updateApplicant(newApp);
                    $(event.target).closest('div').find('button').button({'disabled': false});
                    $(event.target).parent().button({'disabled': true});
                    $("#dialog").data('content', 'Information updated');
                    $('#dialog').dialog('open');
                } else {
                    //post new applicant's cv to repository, async
                    postCV(newApp.id);
                    $(event.target).closest('div.info').find('input').val('');
                    $(event.target).closest('div.info').find('span.file-holder').text('');
                    //applicant was sent to server and received back. now we need to add him to other applicants
                    // and rerender part of the page
                    createApplicant(newApp);
                    //notify applicant, say "your application was registered"
                    notify([
                        {
                            applicantId: newApp.id,
                            groupId: groupID,
                            responsibleHrId: getHRID()
                        }
                    ]);
                }
                //remove validation highlights
                var inputs = $(event.target).closest('div.info').find('input');
                inputs.removeClass('ui-state-highlight');
                inputs.removeClass('ui-state-error');
            },
            error: function () {
                $("#dialog").data('content', 'Failed to create/change applicant!');
                $('#dialog').dialog('open');
            }
        });
    }
};
validateInput = function (target) {
    //was validation successful?
    var result = true;
    //validate every input
    $(target).each(function (index, element) {
        var tmpresult = true;
        //if we're validating cv input
        if ($(element).hasClass('cv')) {
            tmpresult = $(element).val().length != 0;
            if (!tmpresult) {
                $("#dialog").data('content', 'Select the CV file!');
                $('#dialog').dialog('open');
            }
        } else {
            //check whether input data matches input pattern
            var regex = $(element).attr('pattern');
            regex = new RegExp(regex);
            var inputVal = $(element).val();
            tmpresult = regex.test(inputVal);
        }
        //change style of current input according to it's validation status
        changeStyle(tmpresult, element);
        //in case at least one validation failed, return false
        if (!tmpresult) {
            result = tmpresult;
        }
    });
    return result;
};
//take file and send it to repo
postCV = function (id) {
    var formData = new FormData();
    var file = $('input:file')[0].files[0];
    formData.append('file', file);
    $.ajax({
        async: true,
        url: '/repository/doc/' + id,
        data: formData,
        type: 'POST',
        dataType: "json",
        cache: false,
        contentType: false,
        processData: false,
        error: function () {
            $("#dialog").data('content', 'Failed to upload CV!');
            $('#dialog').dialog('open');
        }
    });
};
validateDate = function (target) {
    var d = new Date();
    //the way we validate date: should it be before today or after?
    var before = ($(target).attr('before') === "true");
    var selectedDateStr = $(target).val();
    //date from input
    var selectedDate = new Date(selectedDateStr);
    //wrong format
    if (!selectedDate) {
        return false;
    }
    var result;
    //if selected date should be before today
    if (before) {
        result = selectedDate > d;
    } else {
        //otherwise
        result = selectedDate < d;
    }
    //let user know if validation was successful
    changeStyle(result, target);
    return result;
};
changeStyle = function (condition, target) {
    if ($(target).attr('type') == 'file') {
        target = $(target).closest('div.info').find('input.NFI-filename');
    }
    if (condition) {
        $(target).removeClass("ui-state-error").addClass("ui-state-highlight");
    } else {
        $(target).removeClass("ui-state-highlight").addClass("ui-state-error");
    }
};
//make a js object to send to server
buildApplicant = function (input) {
    var applicant = {};
    //we take each input
    $(input).each(function (index, element) {
        //and add a field to the object with a key-value pair corresponding
        //to input's name-value pair
        var name = $(element).attr('name');
        if (name) {
            applicant[name] = $(element).val();
        }
    });
    return applicant;
};
//we got an applicant from server and need to add it to the page
createApplicant = function (input) {
    //set actual status
    input['status'] = 'NOT_SCHEDULED';
    //we'll send actual status and rank to the group-manager
    var tosend = {};
    tosend[input.id] = {};
    tosend[input.id]['status'] = input['status'];
    tosend[input.id]['rank'] = -1;
    //parse applicant's birthday
    var date = new Date(input.birthday);
    input.birthday = ('0' + +(date.getMonth() + 1)).slice(-2) + '/' + ('0' + date.getDate()).slice(-2) + '/' + date.getFullYear();
    $.ajax({
        async: false,
        url: '/groups/' + groupID + '/applicants',
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(tosend),
        type: 'PUT',
        success: function () {
            //now group knows status and rank of an applicant
            //so we can add him to the array of applicants on the page
            applicants.push({applicant: input, appointment: {availableUsers: userList, scheduledUsers: []}});
            //and rerender part of the page
            var notScheduledRendered = Mustache.render(pageTemplate, {'data': applicants.filter(function (element) {
                //where not scheduled applicants are showed
                return element['applicant']['status'] == 'NOT_SCHEDULED';
            }), 'schedule': true});
            $('.not_scheduled').html(notScheduledRendered);
            $(".accordion").accordion("destroy");
            postRender();
            $("#dialog").data('content', 'Information updated!');
            $('#dialog').dialog('open');
        },
        error: function () {
            $("#dialog").data('content', 'Failed to assign status to the applicant within a group!');
            $('#dialog').dialog('open');
        }
    });
};
parseTime = function (input) {
    var split = input.split(/[,\-\.:]/);
    var result = split[0] * 60 * 60 * 1000;//hours to millis
    if (split.length > 1) {
        result += split[1] * 60 * 1000;//+minutes to millis
    }
    return result;
};
userIDByName = function (names) {
    var result = [];
    $(names).each(function (index, name) {
        $(userList).each(function (index, user) {
            if (name == user.name + ' ' + user.surname) {
                result.push(user.id);
            }
        });
    });
    return result;
};
parseAppointment = function (appointment) {
    //appointment contains list of IDs of scheduled users
    var scheduledUsers = getUsersByIDList(appointment['userIdList']);
    //rest of users
    var availableUsers = [];
    userList.forEach(function (element) {
        var clone = JSON.parse(JSON.stringify(element));
        if ($.inArray(element, scheduledUsers) != -1) {
            clone['disabled'] = true;
        }
        availableUsers.push(clone);
    });
    //parse date
    var date = new Date(appointment.startTime);
    var startDate = ('0' + +(date.getMonth() + 1)).slice(-2) + '/' + ('0' + date.getDate()).slice(-2) + '/' + date.getFullYear();
    var startTime = ('0' + date.getHours()).slice(-2) + ':' + ('0' + date.getMinutes()).slice(-2);
    return {'availableUsers': availableUsers,
        'scheduledUsers': scheduledUsers, 'durationTime': appointment.durationTime / 60 / 1000,
        'startDate': startDate,
        'startTime': startTime}
};
//add user to the appointment
addUser = function (event) {
    //div where the action happens
    var parentdiv = $(event.target).parent().closest("div");
    //select which contains list of available users
    var select = $(parentdiv).find("select.users");
    //selected user
    var selectedOption = $(select).children(":selected");
    //select which contains scheduled users
    var scheduled = $(parentdiv).find("select.scheduled");
    //make selected option disabled and non-selected
    $(selectedOption).prop("disabled", true);
    $(selectedOption).prop("selected", false);
    //add selected user to the list of scheduled users
    $('<option value=' + $(selectedOption).val() + '>'
        + $(selectedOption).text() + '</option>').appendTo(scheduled);
    var scheduledNumber = $(scheduled).children("option").length;
    var usersNumber = $(select).children("option").length;
    //all users where scheduled
    if (scheduledNumber == usersNumber) {
        //disable add-user button
        $(event.target).parent().button("disable");
    }
    //none of users was scheduled
    if (scheduledNumber != 0) {
        //disable remove-user button
        $(parentdiv).find(".removeUser").button({disabled: false});
    }
    $(select).selectmenu("refresh");
    $(scheduled).selectmenu("refresh");
};
//remove user from the list of scheduled users
removeUser = function (event) {
    //div where action happens
    var parentdiv = $(event.target).parent().closest("div");
    //select containing list of scheduled users
    var scheduled = $(parentdiv).find("select.scheduled");
    //selected user
    var selectedOption = $(scheduled).children(":selected");
    //select containing
    var users = $(parentdiv).find("select.users");
    var selectedUser = $(users).children("option[value='" + $(selectedOption).val() + "']");
    //remove user from list of scheduled users
    $(selectedOption).remove();
    //and make it enabled in the list of available users
    $(selectedUser).prop("disabled", false);
    $(selectedUser).prop("selected", true);
    var scheduledNumber = $(scheduled).children("option").length;
    var usersNumber = $(users).children("option:enabled").length;
    //if none of users was scheduled
    if (scheduledNumber == 0) {
        //disable remove-user button
        $(event.target).parent().button("disable");
    }
    //if at least one user was scheduled
    if (usersNumber != 0) {
        //enable add-user button
        $(parentdiv).find(".addUser").button({disabled: false});
    }
    $(scheduled).selectmenu("refresh");
    $(users).selectmenu("refresh");
};
updateApplicant = function (applicant) {
    $(applicants).each(function (index, element) {
        if (element.applicant.id == applicant.id) {
            element.applicant = applicant;
        }
    });
};
getUsersByIDList = function (IDList) {
    return userList.filter(function (element) {
        return $.inArray(element.id, IDList) > -1;
    });
};
writeCookie = function (cname, cvalue) {
    var d = new Date();
    d.setTime(d.getTime() + (24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
};