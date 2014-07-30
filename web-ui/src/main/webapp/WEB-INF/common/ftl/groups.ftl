<#import "header_footer.ftl" as header_footer/>

<@header_footer.main_page>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="groups/js/mustache.js"></script>
<script src="groups/js/jqueryFunctions.js"></script>
<script src="groups/js/createGroupDialog.js"></script>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/ui-lightness/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="groups/css/group.css">
<link rel="stylesheet" type="text/css" href="groups/css/jquery.timepicker.css">
<script src="groups/js/jquery.validate.js"></script>
<script src="groups/js/jquery.timepicker.js"></script>

<script>
    $(document).ready(function(){
        viewGroups();
    });
</script>

<div id="dialog-form-add-group" class="dialog" style="padding: 0px;">
    <div class="inner">
        <div id="user-box">
            <form id="userForm" method="POST">
                <H2><div id="formTitle"></div></H2>
                <fieldset>
                    <div class="fieldgroup">
                        <div class="headline name" >
                            <label for="gName">
                                Name:
                            </label>
                        </div>
                        <div class="fields">
                            <input name="groupName" id="gName" class="inputDLP"/>
                        </div>
                    </div>

                    <div class="fieldgroup">
                        <div class="headline address" >
                            <label for="gAddress">
                                Address:
                            </label>
                        </div>
                        <div class="fields">
                            <input name="groupAddress" id="gAddress" class="inputDLP"/>
                        </div>
                    </div>

                    <div class="fieldgroup">
                        <div class="headline course">
                            <label for="gCourse">
                                Course:
                            </label>
                        </div>
                        <select name="groupCourse" id="gCourse" class="inputDLP roleCombobox">
                        </select>
                    </div>
                    <div class="fieldgroup">
                        <div class="headline capacity" >
                            <label for="gCapacity">
                                Capacity:
                            </label>
                        </div>
                        <div class="fields">
                            <input name="groupCapacity" id="gCapacity" class="inputDLP"/>
                        </div>
                    </div>

                    <div class="fieldgroup">
                        <div class="headline startBoardingDate" >
                            <label for="gStartBoardingDate">
                                Start boarding:
                            </label>
                        </div>
                        <div class="fields">
                            <input name="groupStartBoardingDate" id="gStartBoardingDate" class="inputDLP">
                        </div>
                    </div>

                    <div class="fieldgroup">
                        <div class="headline startDate">
                            <label for="gStartDate">
                                Start date:
                            </label>
                        </div>
                        <div class="fields">
                            <input name = "groupStartDate" id="gStartDate" class = "inputDLP">
                        </div>
                    </div>

                    <div class="fieldgroup">
                        <div class="headline startTime" >
                            <label for="gStartTime">
                                Start time:
                            </label>
                        </div>
                        <div class="fields">
                            <input class="timePicker" name = "groupStartTime" id="gStartTime">
                        </div>
                    </div>


                    <div class="fieldgroup">
                        <div class="headline endDate" >
                            <label for="gEndDate">
                                End date:
                            </label>
                        </div>
                        <div class="fields">
                            <input name = "groupEndDate" id="gEndDate" class = "inputDLP">
                        </div>
                    </div>
                    <br>
                    <button id="saveUButton" class="btn saveButton">SAVE</button>
                    <button id="cancelUButton" class="btn cancelButton">CANCEL</button>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<div id="dialog-form-delete-group" class="dialog" style="padding: 0px;">
    <div class="inner">
        <div id="delete-user-box">
            <div class="headline title" >
                Are you sure that you want to delete selected group?
            </div>
            <br>
            <button id="okDUButton" class="btn okButton">OK</button>
            <button id="cancelDUButton" class="btn cancelButton">CANCEL</button>
        </div>
    </div>
</div>

<div id="dialog-information" class="dialog" style="padding: 0px;">
    <div class="inner">
        <div id="delete-user-box">
            <div class="headline title" id = "Information" >
            </div>
            <br>
            <button id="okInfButton" class="btn okButton">OK</button>
        </div>
    </div>
</div>


<br>
<div class="container">
    <div class="row-fluid">
        <div class="span12">
            <div class="titleBox">
                <h1 class="huge">Groups
            <span>
                <select name="select1" id="statuses" onchange="viewGroups()">
                    <option value="All groups">All groups</option>
                    <option value="BOARDING">Boarding</option>
                    <option value="IN_PROCESS">In progress</option>
                    <option value="PLANNED">Planned</option>
                    <option value="FINISHED">Finished</option>
                </select>
                   <select name="select2" id="courses" onchange="viewGroups()">

                   </select>
            </span>
                </h1>
            </div>
        </div>
    </div>

    <div class="row-fluid">
        <div class="span12">
            <ul class="portfolioList col4" id="portfolio1">
            </ul>
        </div>
    </div>
</div>

</@header_footer.main_page>