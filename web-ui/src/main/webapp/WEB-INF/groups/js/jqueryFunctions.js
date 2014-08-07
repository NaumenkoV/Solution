var functionCalled = false;


function viewGroups() {
    if (!functionCalled) {
        functionCalled = true;
        createCourseMenu("#courses", "All courses");
    }
    var template;
    jQuery.get("groups/template/iconTemplate", function (data) {
        template = data;
    });
    var output = "";
    var createGroupHtml = "";
    var selectedStatus = $("#statuses").val();
    var selectedCourse = $("#courses").val();
    var getGroupsUrl;
    if (selectedStatus == "All groups") {
        getGroupsUrl = "/groups/allGroups";
    }
    else {
        getGroupsUrl = "/groups/status/" + selectedStatus;
    }
    jQuery.get("groups/template/createGroupHtml", function (data) {
        createGroupHtml = data;
    });
    $.ajax({
        url: location.origin + getGroupsUrl,
        dataType: "json",
        type: "GET",
        success: function (data) {
            var image;
            for (var index = 0; index < data.length; index++) {
                if (data[index].course.name == selectedCourse || selectedCourse == "All courses") {
                    var view = {
                        ref: 'group?groupID=' + data[index].groupID,
                        image: data[index].course.imageRef,
                        courseName: "Course : " + data[index].course.name,
                        groupName: data[index].groupName,
                        groupId: data[index].groupID
                    }
                    output += Mustache.render(template, view);
                }
            }
            output += createGroupHtml;
            $("#portfolio1").html(output);
        },
        error: function (data) {
            console.log("" + data);
        }
    });


}

var groupId;
var editDialog;
function viewAddDialog() {
    editDialog = false;
    $("#dialog-form-add-group").data('content', 'Group added');
    $('#dialog-form-add-group').dialog('open');
}

function viewDeleteDialog(id) {
    groupId = id;
    $("#dialog-form-delete-group").data('content', 'Group deleted');
    $('#dialog-form-delete-group').dialog('open');
}
function viewInformationDialog() {
    $("#dialog-information").data('content', 'Group deleted');
    $('#dialog-information').dialog('open');
}

function viewEditInformationDialog(id) {
    groupId = id;
    editDialog = true;
    $("#dialog-form-add-group").data('content', 'Group edited');
    $('#dialog-form-add-group').dialog('open');
}
