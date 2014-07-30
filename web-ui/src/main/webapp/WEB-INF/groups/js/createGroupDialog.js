var editedGroup;
$(function () {
    $("#gStartDate").datepicker();
    $("#gEndDate").datepicker();
    $("#gStartBoardingDate").datepicker();
    $("#gStartTime").timepicker();
    $('#gStartTime').timepicker({ 'step': 10 });
    $('#gStartTime').timepicker({ 'timeFormat': 'H:i' });
    $("#dialog-form-add-group").dialog({
        modal: true,
        autoOpen: false,
        width: 'auto',
        resizable: false,
        dialogClass: 'dialog',
        show: { effect: "fade", duration: 800 },
        hide: { effect: "fade", duration: 800 },
        open: function(){
            createCourseMenu("#gCourse", "Select group course");
            if (editDialog) {
                editedGroup = getGroupById(groupId);
                var startDate = calculateStringDate(new Date(editedGroup.startTime));
                var boardingDate = calculateStringDate(new Date(editedGroup.startBoardingTime));
                var endDate = calculateStringDate(new Date(editedGroup.endTime));
                var startTime = calculateStringTime(new Date(editedGroup.startTime));
                $("#gName").val(editedGroup.groupName);
                $("#gCapacity").val(editedGroup.capacity);
                $("#gStartDate").val(startDate);
                $("#gStartTime").val(startTime);
                $("#gEndDate").val(endDate);
                $("#gStartBoardingDate").val(boardingDate);
                $("#gCourse").val(editedGroup.course.name);
                $("#gAddress").val(editedGroup.address);
            }
        },
        close: function () {
            $("#gName").val("");
            $("#gCapacity").val("");
            $("#gStartDate").val("");
            $("#gEndDate").val("");
            $("#gAddress").val("");
            $('#gStartTime').val("");
            $('#gStartBoardingDate').val("");
            $("#gCourse").val("Select group course");
        }
    });

    function calculateStringDate(date) {
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var year = date.getFullYear();
        var stringDate = "" + month + "/" + day + "/" + year;
        return stringDate;
    }

    function calculateStringTime(date) {
        var minutes = date.getMinutes();
        var hours = date.getHours();
        var stringTime = hours + ":" + minutes;
        return stringTime;
    }

    function getGroupById(id) {
        var group;
        $.ajax({
            url: location.origin + "/groups/" + id,
            dataType: "json",
            type: "GET",
            async: false,
            success: function (data) {
                group = data;
            },
            error: function (data) {
                console.log("" + data);
            }
        });
        return group;
    }

    $("#dialog-information").dialog({
        modal: true,
        autoOpen: false,
        width: 'auto',
        resizable: false,
        dialogClass: 'dialog',
        show: { effect: "fade", duration: 800 },
        hide: { effect: "fade", duration: 800 }
    });

    $("#dialog-form-delete-group").dialog({
        modal: true,
        autoOpen: false,
        width: 'auto',
        resizable: false,
        dialogClass: 'dialog',
        show: { effect: "fade", duration: 800 },
        hide: { effect: "fade", duration: 800 }
    });

    $("#cancelUButton").click(function (e) {
        e.preventDefault();
        $("#dialog-form-add-group").dialog("close");
    });

    $("#cancelDUButton").click(function (e) {
        e.preventDefault();
        $("#dialog-form-delete-group").dialog("close");
    });

    $("#okInfButton").click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        $("#dialog-information").dialog("close");
    });

    $("#okDUButton").click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        deleteGroup(groupId);
        $("#dialog-form-delete-group").dialog("close");
    });

    function deleteGroup(groupId) {
        $.ajax({
            url: location.origin + "/groups/" + groupId,
            contentType: 'application/json',
            mimeType: 'application/json',
            dataType: "json",
            async: false,
            type: "DELETE",
            success: function (data) {
                location.reload();
            },
            error: function (data) {
                $('#Information').html(data.responseJSON.reason);
                viewInformationDialog();
                console.log("" + data);
            }
        });
    };

    $("#saveUButton").click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        if ($("#userForm").valid()) {
            if (!editDialog) {
                var group = getGroupFromForm();
                sendGroup(group);
            }
            else {
                var group = getGroupFromForm();
                updateGroup(group);
            }
            $("#dialog-form-add-group").dialog("close");
        }
    });


    function getGroupFromForm() {
        var course = {};
        course.name = $("#gCourse").val();
        var group =  {};
        if (editDialog) {
            group.groupID = editedGroup.groupID;
            group.applicants = editedGroup.applicants;
        }
        else {
            group.groupID = "1";
        }
        group.groupName = $("#gName").val();
        group.address = $("#gAddress").val();
        group.capacity = $("#gCapacity").val();
        group.startBoardingTime = new Date($("#gStartBoardingDate").val()).getTime();
        group.startTime = new Date($("#gStartDate").val()).getTime() + $("#gStartTime").timepicker('getSecondsFromMidnight') * 1000;
        group.endTime = new Date($("#gEndDate").val()).getTime();
        group.course = course;
        return group;
    };

    function updateGroup(group) {
        var jsonGroup = JSON.stringify(group);
        $.ajax({
            url: location.origin + "/groups/editGroup",
            contentType: 'application/json',
            mimeType: 'application/json',
            dataType: "json",
            async: false,
            type: "PUT",
            data: jsonGroup,
            success: function (data) {
                location.reload();
            },
            error: function (data) {
                $('#Information').html(data.responseJSON.reason);
                viewInformationDialog();
                console.log("" + data);
            }
        });
    }

    function sendGroup(group) {
        var jsonGroup = JSON.stringify(group);
        var success = true;
        $.ajax({
            url: location.origin + "/groups/addGroup",
            contentType: 'application/json',
            mimeType: 'application/json',
            dataType: "json",
            async: false,
            type: "POST",
            data: jsonGroup,
            success: function (data) {
                location.reload();
            },
            error: function (data) {
                console.log("" + data);
            }
        });
        return success;
    };

    jQuery.validator.addMethod("greaterThanBoardingDate",
        function (value, element, params) {
            var startDate = new Date(value).getTime();
            var boardingDate = new Date($(params).val()).getTime();
            return boardingDate < startDate;
        });

    jQuery.validator.addMethod("greaterThan",
        function (value, element, params) {
            if (!/Invalid|NaN/.test(new Date(value))) {
                return new Date(value) > new Date($(params).val());
            }

            return isNaN(value) && isNaN($(params).val())
                || (Number(value) > Number($(params).val()));
        });

    jQuery.validator.addMethod("greaterThanToday",
        function (value, element, params) {
            if (!/Invalid|NaN/.test(new Date(value))) {
                return new Date(value) > params;
            }
            return isNaN(value) && isNaN(params)
                || (Number(value) > Number(params));
        });

    jQuery.validator.addMethod("notDefaultValue", function (value) {
        return value != "Select group course";
    });

    $("#userForm").validate({
        rules: {
            groupName: {
                required: true,
                minlength: 3,
                maxlength: 7
            },
            groupAddress: {
                required: true,
                minlength: 8,
                maxlength: 40
            },
            groupCourse: {
                required: true,
                notDefaultValue: true
            },
            groupCapacity: {
                required: true,
                min : 1,
                number : true
            },
            groupStartBoardingDate: {
                required: true,
                date: true
            },
            groupStartDate: {
                required: true,
                date: true,
                greaterThanBoardingDate: "#gStartBoardingDate"
            },
            groupStartTime:{
                required:true
            },
            groupEndDate: {
                required: true,
                date: true,
                greaterThan: "#gStartDate"
            }
        },
        messages: {
            groupName: {
                required: "Group name is required",
                minlength: jQuery.validator.format("At least {0} characters is required"),
                maxlength: jQuery.validator.format("No more than {0} characters is allowed")
            },
            groupAddress: {
                required: "Group address is required.",
                minlength: jQuery.validator.format("At least {0} characters is required"),
                maxlength: jQuery.validator.format("No more than {0} characters is allowed")
            },
            groupCourse: {
                required: "Group course is required.",
                notDefaultValue: "Choose group course"
            },
            groupCapacity: {
                required: "Group capacity is required.",
                number : "Should be a correct number.",
                min: jQuery.validator.format("At least 1 student is required")
            },
            groupStartBoardingDate: {
                required: "Start boarding date is required",
                date: "Should be a correct date format"
            },
            groupStartDate: {
                required: "Start date is required.",
                date: "Should be a correct date format.",
                greaterThanBoardingDate: "Should be greater than boarding date"
            },
            groupStartTime:{
                required : "Start time is required"
            },
            groupEndDate: {
                required: "End date is required.",
                date: "Should be a correct date.",
                greaterThan: "Must be greater than start date"
            }
        }
    });
});

function createCourseMenu(position, value) {
    $.ajax({
        url: location.origin + "/groups/courses",
        dataType: "json",
        type: "GET",
        async: false,
        success: function (data) {
            var output = "<option value='' id='defaultChoose'  selected></option>";
            var template = "<option value={{courseName}}>{{courseName}}</option>";
            for (var index = 0; index < data.length; index++) {
                var view = {
                    courseName: data[index].name
                }
                output += Mustache.render(template, view);
            }
            $(position).html(output);
            $("#defaultChoose").attr('value', value);
            $("#defaultChoose").html(value);
        },
        error: function (data) {
            console.log("" + data);
        }
    });
}

