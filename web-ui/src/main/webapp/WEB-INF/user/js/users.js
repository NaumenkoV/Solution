$(function() {
    var roleMap = {};
    var roleArray = [];
    var userMap = {};
    var pageTemplate;
    var requestType = "";

    function getUserFromForm() {
        var userRole = {};
        userRole.id = $('#uRole option:selected').attr('id');
        userRole.name = $('#uRole option:selected').text();

        var user = $("#dialog-form-add-user").data("user") ? $("#dialog-form-add-user").data("user") : {};
        user.name = $('#uName').val();
        user.surname = $('#uSurname').val();
        user.password = $('#uPassword').val();
        user.email = $('#uEmail').val();
        user.phone = $('#uPhone').val();
        user.role = userRole;

        return user;
    };

    function sendUser(user) {
        var failedAction = (requestType == "POST") ? 'add' : 'edit';
        if((requestType == "POST") && userWithThisEmailExists(user)){
            $("#userErrContent").append('Impossible to ' + failedAction + ' user. User with this email already exists.');
            $("#dialog-form-error-user").dialog( "open" );
            return false;
        }

        var success = true;
        var userInJson = JSON.stringify(user);
        $.ajax({
            url: '/users/',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            type: requestType,
            data: userInJson,
            error: function (data) {
                success = false;
                $("#userErrContent").append('Failed to ' + failedAction + ' user. ' + data.statusText);
                $("#dialog-form-error-user").dialog( "open" );
            }
        });
        return success;
    };

    function deleteUser(user) {
        var success = true;
        $.ajax({
            url: '/users/' + user.id,
            async: false,
            type: 'DELETE',
            error: function (data) {
                success = false;
                $("#userErrContent").append('Failed to delete user. ' + data.statusText);
                $("#dialog-form-error-user").dialog( "open" );
            }
        });
        return success;
    };

    function userWithThisEmailExists(user) {
        var success = false;
        $.ajax({
            url: '/users/param?email=' + user.email,
            async: false,
            type: 'GET',
            success: function (data) {
                var existingUser = data;
                success = (existingUser.email == user.email);
            },
            error: function (data) {
                if (data.responseText) {
                    var response = JSON.parse(data.responseText);
                    if (response.reason && response.reason == 'There is no user with such email') {
                        return;
                    }
                }
                $("#userErrContent").append(data.statusText);
                $("#dialog-form-error-user").dialog( "open" );
            }
        });
        return success;
    }

    $( "#dialog-form-error-user" ).dialog({
        modal: true,
        autoOpen: false,
        width: 'auto',
        resizable: false,
        dialogClass: 'dialog',
        close: function() {
            $("#userErrContent").empty();
        }
    });

   $( "#dialog-form-delete-user" ).dialog({
        modal: true,
        autoOpen: false,
        width: 510,
        resizable: false,
        dialogClass: 'dialog'
    });

    $( "#dialog-form-add-user" ).dialog({
        modal: true,
        autoOpen: false,
        width: 'auto',
        resizable: false,
        dialogClass: 'dialog',
        open: function() {

            $.each(roleArray, function( index, val ) {
                $("#uRole").append("<option id='" + val.role_id + "'>" + val.name + "</option>");
            });

            var user = $(this).data("user");
            if (user != null) {
                requestType = "PUT";
                $( "#formTitle" ).append( "Edit user" );

                $('#uName').val(user.name);
                $('#uSurname').val(user.surname);
                $('#uPassword').val(user.password);
                $('#uEmail').val(user.email);
                $('#uPhone').val(user.phone);
                $('#uRole').val(user.role.name);

            }else{
                requestType = "POST";
                $( "#formTitle" ).append( "Add user" );
            }
        },
        close: function(){
            $(".inputDLP").val('');
            $( "#uForm" ).attr( "method", "" );
            $( "#formTitle" ).empty();
            $( "#uRole" ).empty();
            $( "#uRole" ).append( "<option value=''>Select user role</option>" );
            $( "label.error").hide();
        }
    });

    //user management buttons
    $( "#addUserButton" ).click(function() {
        $( "#dialog-form-add-user" ).data("pass_changed", true);
        $( "#dialog-form-add-user" ).data("user", null);
        $( "#dialog-form-add-user" ).dialog( "open" );
    });

    $("#saveUButton").click( function(e){
        e.preventDefault();
        e.stopPropagation();

        if($("#userForm").valid()){
            var userToSend = getUserFromForm();
            if (sendUser(userToSend)) {
                $( "#dialog-form-add-user" ).dialog( "close" );
                reloadTabContent(userToSend.role.id);
            }
        }
    });
    $( "#cancelUButton" ).click(function(e) {
        e.preventDefault();
        $( "label.error").hide();
        $( "#dialog-form-add-user" ).dialog( "close" );
    });

    $("#okDUButton").click( function(e){
        e.preventDefault();
        var userToDelete = $( "#dialog-form-delete-user" ).data("user");
        if (userToDelete) {
            if (deleteUser(userToDelete)) {
                reloadTabContent(userToDelete.role.id);
            }
            $( "#dialog-form-delete-user" ).dialog( "close" );
        }
    });

    $("#okEUButton").click( function(e){
        e.preventDefault();
        $( "#dialog-form-error-user" ).dialog( "close" );
    });

    $( "#cancelDUButton" ).click(function() {
        $( "#dialog-form-delete-user" ).dialog( "close" );
    });

    $("#userForm").validate({
        rules: {
            userName: {
                required: true,
                minlength: 2,
                maxlength: 40
            },
            userSurname: {
                required: true,
                minlength: 2,
                maxlength: 40
            },
            userPassword: {
                required: {
                    depends: function(element) {
                        return $( "#dialog-form-add-user" ).data("pass_changed");
                    }
                },
                minlength: 5,
                maxlength: 40
            },
            userPhone:{
                required: false,
                maxlength: 30
            },
            userEmail: {
                required: true,
                email: true
            },
            userRole: {
                required: true
            }
        },
        messages: {
            userName: {
                required: "User name is required.",
                minlength: jQuery.validator.format("At least {0} characters required in \"Name\" field."),
                maxlength: jQuery.validator.format("Name length should not exceed {0} characters.")
            },
            userSurname: {
                required: "User surname is required.",
                minlength: jQuery.validator.format("At least {0} characters required in \"Surname\" field."),
                maxlength: jQuery.validator.format("Surname length should not exceed {0} characters.")
            },
            userPassword: {
                required: "User password is required.",
                minlength: jQuery.validator.format("At least {0} characters required in \"Password\" field."),
                maxlength: jQuery.validator.format("Password length should not exceed {0} characters.")
            },
            userPhone: {
                maxlength: jQuery.validator.format("Phone length should not exceed {0} characters.")
            },
            userEmail: {
                required: "User email is required.",
                email: "Email address must be in the format of name@domain.com"
            },
            userRole: {
                required: "User role is required."
            }
        }
    });

    $( "#showHidePassword" ).change(function() {
        if($("#showHidePassword").is(':checked'))
            $("#uPassword").attr("type","text");
        else
            $("#uPassword").attr("type","password");
    });

    $( "#uPassword" ).change(function() {
        $( "#dialog-form-add-user" ).data("pass_changed", true);
    });

    $.ajax({
        async: false,
        url: '/ui/user/mst/users_template.mst',
        type: "GET",
        success: function (data) {
            pageTemplate = data;
        }
    });

    reloadTabContent(0);

    function reloadTabContent (activeRoleTabId) {
        roleMap = {};
        roleArray = [];
        userMap = {};

        $.ajax({
            async: false,
            url: '/users/roles',
            dataType: "json",
            type: "GET",
            success: function (roleList) {
                $.each( roleList, function(index, role ) {
                    roleMap[role.id] = {
                        "role_id" : role.id,
                        "active"  : (activeRoleTabId == role.id ? "active" : ""),
                        "name": role.name,
                        "users": []
                    };
                });
            }
        });

        $.ajax({
            async: false,
            url: '/users',
            dataType: "json",
            type: "GET",
            success: function (userList) {
                if (userList) {
                    $.each( userList, function(index, user) {
                        user.password = null; // hide the password hashes
                        userMap[user.id] = user;
                        if(user.role){
                            roleMap[user.role.id].users.push(user);
                        }
                    });
                }

                for (var key in roleMap) {
                    roleArray.push(roleMap[key]);
                }
                if (activeRoleTabId == 0) {
                    roleArray[0].active = "active";
                }

                var rendered = Mustache.render(pageTemplate, {"roles": roleArray});
                $('#content').empty();
                $('#content').html(rendered);
                registerEventHandlers();
            }
        });


        function registerEventHandlers() {
            $( ".btn-edit-user" ).click(function() {
                var userId = $(this).attr("id").substring("editUser".length);
                $( "#dialog-form-add-user" ).data("pass_changed", false);
                $( "#dialog-form-add-user" ).data("user", userMap[userId]);
                $( "#dialog-form-add-user" ).data("roles", roleArray);
                $( "#dialog-form-add-user" ).dialog( "open" );
            });
            $( ".btn-del-user" ).click(function() {
                var userId = $(this).attr("id").substring("deleteUser".length);
                $( "#dialog-form-delete-user" ).data("user", userMap[userId]);
                $( "#dialog-form-delete-user" ).dialog( "open" );
            });
        }
    }
});
