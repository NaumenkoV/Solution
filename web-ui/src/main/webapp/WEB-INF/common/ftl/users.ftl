<#import "header_footer.ftl" as header_footer/>

<@header_footer.main_page>

    <link rel="stylesheet" href="/common/css/jquery-ui.css">
    <link rel="stylesheet" href="/common/css/icons/fontello/css/fontello.css">
    <link rel="stylesheet" type="text/css" href="user/css/users.css">

    <script src="/common/js/jquery-1.9.1.js"></script>
    <script src="/common/js/bootstrap.min.js"></script>
    <script src="/common/js/mustache.js"></script>
    <script src="/common/js/jquery-ui.js"></script>

    <script src="/common/js/jquery.form.js"></script>
    <script src="/common/js/jquery.validate.js"></script>
    <script src="user/js/users.js"></script>

    <div class="container">
        <div class="row-fluid">
            <div class="span12">
                <div class="titleBox">
                    <h1 class="huge">Users <span>  </span>
                    </h1>
                </div>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span12">
                <div class="users lineBox right">
                    <div class="inner">
                        <a id="addUserButton" class="btn btn-icon left btn-primary">
                            <span class="entypo plus-squared">
                                <i></i>
                            </span>
                            Add
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="row-fluid">
            <div id="content">

            </div>
        </div>
    </div>


    <!-- user dialog form -->
    <div id="dialog-form-add-user" class="dialog" style="padding: 0px;">
        <div class="inner">
            <div id="user-box">
                <form id="userForm" method="POST">
                    <H2><div id="formTitle"></div></H2>
                    <fieldset>
                        <div class="fieldgroup">
                            <div class="headline name" >
                                <label for="uName">
                                    Name:
                                </label>
                            </div>
                            <div class="fields">
                                <input name="userName" id="uName" class="inputDLP"/>
                            </div>
                        </div>

                        <div class="fieldgroup">
                            <div class="headline surname" >
                                <label for="uSurname">
                                    Surname:
                                </label>
                            </div>
                            <div class="fields">
                                <input name="userSurname" id="uSurname" class="inputDLP"/>
                            </div>
                        </div>

                        <div class="fieldgroup">
                            <div class="headline password" >
                                <label for="uPassword">
                                    Password:
                                </label>
                            </div>
                            <div class="fields">
                                <input name="userPassword" id="uPassword" class="inputDLP" type="password" />
                                <span class="shp">
                                    <input type="checkbox" id="showHidePassword">Show password
                                </span>
                            </div>
                        </div>

                        <div class="fieldgroup">
                            <div class="headline email" >
                                <label for="uEmail">
                                    Email:
                                </label>
                            </div>
                            <div class="fields">
                                <input name="userEmail" id="uEmail" class="inputDLP"/>
                            </div>
                        </div>

                        <div class="fieldgroup">
                            <div class="headline phone" >
                                <label for="uPhone">
                                    Phone:
                                </label>
                            </div>
                            <div class="fields">
                                <input name="userPhone" id="uPhone" class="inputDLP"/>
                            </div>
                        </div>

                        <div class="fieldgroup">
                            <div class="headline role">
                                <label for="uRole">
                                    Role:
                                </label>
                            </div>
                            <select name="userRole" id="uRole" class="inputDLP roleCombobox">
                                <option value="">Select user role</option>
                            </select>
                        </div>
                        <br>
                        <button id="saveUButton" class="btn saveButton">SAVE</button>
                        <button id="cancelUButton" class="btn cancelButton">CANCEL</button>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>

    <!-- delete user dialog form -->
    <div id="dialog-form-delete-user" class="dialog" style="padding: 0px;">
        <div class="inner">
            <div id="delete-user-box">
                <div class="headline title" >
                    Are you sure that you want to delete selected user?
                </div>
                <br>
                <button id="okDUButton" class="btn okButton">OK</button>
                <button id="cancelDUButton" class="btn cancelButton">CANCEL</button>
            </div>
        </div>
    </div>

    <!-- error user dialog form -->
    <div id="dialog-form-error-user" class="dialog" style="padding: 0px;">
        <div class="inner">
            <div id="error-user-box">
                <div class="headline title" id="userErrContent">

                </div>
                <br>
                <button id="okEUButton" class="btn okButton">OK</button>
            </div>
        </div>
    </div>


</@header_footer.main_page>
