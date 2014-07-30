<#import "header_footer.ftl" as header_footer/>

<@header_footer.main_page>

    <link rel="stylesheet" href="/common/css/jquery-ui.css">
    <link rel="stylesheet" href="/common/css/icons/fontello/css/fontello.css">
    <link rel="stylesheet" type="text/css" href="questions/css/questions.css">
    <link rel="stylesheet" type="text/css" href="questions/css/jquery_dataTables.css">

    <script src="/common/js/jquery-1.9.1.js"></script>
    <script src="/common/js/bootstrap.min.js"></script>

    <script src="/common/js/mustache.js"></script>
    <script src="/common/js/jquery-ui.js"></script>

    <script src="questions/js/jquery.raty.js"></script>
    <script src="questions/js/jquery_dataTables.js"></script>

    <script src="/common/js/jquery.form.js"></script>
    <script src="/common/js/jquery.validate.js"></script>

    <script src="questions/js/questions.js"></script>

    <div class="container">
        <div class="row-fluid">
            <div class="span12">
                <div class="titleBox">
                    <h1 class="huge">Questions</h1>
                </div>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span12">
                <div class="questions lineBox right">
                    <div class="inner">
                        <a href="#" id="addQButton" class="btn btn-icon left btn-primary"><span class="entypo plus-squared"><i></i></span>Add</a>
                        <a href="#" id="editQButton" class="btn btn-icon left btn-primary"><span class="entypo pencil"><i></i></span>Edit</a>
                        <a href="#" id="deleteQButton" class="btn btn-icon left btn-primary"><span class="entypo cancel-squared"><i></i></span>Delete</a>
                    </div>
                </div>
            </div>			
        </div>
        <div class="row-fluid">
            <div id="content">

            </div>
        </div>
    </div>


    <!-- questions dialog form -->
    <div id="dialog-form-add-question" class="dialog" style="padding: 0px;">
        <div class="inner">
            <div id="questions-box">
                <form id="questionForm" method="POST">
                    <H2><div id="formTitle"></div></H2>
                    <fieldset>
                        <div class="fieldgroup">
                            <div class="headline questionBody" >
                                <label for="qBody">
                                    Question:
                                </label>
                            </div>
                            <div class="fields">
                                <textarea name="questionBody" id="qBody" class="inputDLP"/></textarea>
                            </div>
                        </div>

                        <div class="fieldgroup">
                            <div class="headline questionWeight" >
                                <label for="qWeight">
                                    Question rating:
                                </label>
                            </div>
                            <div class="fields">
                                <div name="questionWeight" id="qWeight" class="inputDLP"/>
                            </div>
                        </div>
                        
                        <br>
                        <button id="saveQButton" class="btn saveButton">SAVE</button>
                        <button id="cancelQButton" class="btn cancelButton">CANCEL</button>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>

    <!-- delete questions dialog form -->
    <div id="dialog-form-delete-question" class="dialog" style="padding: 0px;">
        <div class="inner">
            <div id="delete-question-box">
                <div class="headline title" >
                    Are you sure that you want to delete selected questions?
                </div>
                <br>
                <button id="okDQButton" class="btn okButton">OK</button>
                <button id="cancelDQButton" class="btn cancelButton">CANCEL</button>
            </div>
        </div>
    </div>

    <!-- error questions dialog form -->
    <div id="dialog-form-error-question" class="dialog" style="padding: 0px;">
        <div class="inner">
            <div id="error-question-box">
                <div class="headline title" id="questionsErrContent">

                </div>
                <br>
                <button id="okEQButton" class="btn okButton">OK</button>
            </div>
        </div>
    </div>


</@header_footer.main_page>
