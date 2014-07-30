<!DOCTYPE html>
<html>

<head>
    <title>SoftServe |</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Interview</title>
    <link rel="stylesheet" href="interview/css/reset.css">
    <link rel="stylesheet" href="interview/css/bootstrap-3.2.0-dist/css/bootstrap.css">
    <link rel="stylesheet" href="interview/css/common.css">
    <link rel="stylesheet" href="interview/css/interview.css">
    <link rel="stylesheet" href="interview/css/chat.css">
    <link rel="stylesheet" href="interview/css/applicantPopup.css">
    <link rel="stylesheet" href="interview/css/timer.css">


    <link rel="icon"  href="favicon.ico" type="image/x-icon" />
</head>
<body>
   <div id="startData" userId="${userId}" appointmentId="${appointmentId}"></div>
    <header  ng-controller="ModalDemoCtrl">
        <div id="header-left-group">

            <div id="header-logo"></div>
            <div id="page-title">Interview</div>
        </div>

        <div>

             <a  id="applicant-btn" ng-click="openAppPop()"  href="#"><span class="glyphicon glyphicon-user"></span> {{applicant.name}} {{applicant.surname}}</a>

        </div>

        <div id="header-right-group" >
            <a id="finish-btn" ng-click="openFCPop()" href="#"><span class="glyphicon glyphicon-ok"></span></a>

            <div class="timeOn" ng-class="{'timeUp': isForward}">{{ duration  | date:"mm:ss" }}</div>

            <a id="header-user-profile-btn"><span> <img class="img-rounded" src="interview/img/userpic-small.png" alt="user-pic">  {{user.name}} {{user.surname}}</span> </a>
        </div>
    </header>
    <div ng-controller="QuestionsController" id="main-panel">
    <div id="left-panel" >
        <div class="panel-title">Questions</div>
        <div id="questions-list">
<div class="list-group">
    <a ng-repeat="question in questions" class="list-group-item" ng-class="{'answered': isAnswered(question) ,'active':isActive(question) }" href="#" data-toggle="tab" ng-click="selectQuestion(question)">{{ question.question }}</a>
    </div>
        </div>
        <a id="new-question-btn" class="panel-btn" href="#" ng-click="newQuestion()">
            <span class="glyphicon glyphicon-edit"></span> New Question
        </a>
    </div>
    <div id="central-panel">
        <div class="panel-title">Question Manager</div>
        <div id="question-manager">
            <div id="question-container">
                <textarea id="question-area" placeholder="Enter your question over here ..." required ng-model="question.question"></textarea>
            </div>
            <div id="comment-container">
                <textarea id="comment-area" placeholder="Enter your comment over here ..." required ng-model="question.comment"></textarea>
            </div>
            <div class="rating-container">
                <div id="question-mark">
                <b>Mark:</b>
                   <rating ng-model="question.mark" max="10" state-on="'glyphicon-star'" state-off="'glyphicon-star-empty'" on-hover="hoveringOver(value)" on-leave="hoverMark = null"></rating><b>

                <span id="mark">{{hoverMark || question.mark}}</span>/10</b>
                </div>
                <div id="question-weight">
                <b>Weight:</b>
                   <rating ng-model="question.weight" max="4" state-on="'glyphicon-stop'" state-off="'glyphicon-unchecked'"></rating><b>
                <span id="weight">{{intToWeight(question.weight)}}</span></b>
                </div>
            </div>
        </div>
        <a id="submit-question-btn" class="panel-btn" ng-click="submitQuestion(question)" href="#">
            <span class="glyphicon glyphicon-ok"></span> Submit
        </a>
    </div>
    </div>
   <div id="right-panel" ng-controller="ChatController" ng-init="connect()" >

       <div class="panel-title">Chat/Notifications</div>
          <div id="panel-notifications">
             <div>
               <div ng-switch="ONLINE">
                   <div class="dm-online-text" ng-switch-when="true">ONLINE</div>
                   <div class="dm-offline-text"ng-switch-default>OFFLINE</div>
               </div>
           </div>
           <div class="dm-chat">
               <div ng-repeat="message in messages" class="dm" ng-class="{'sent':message.sent}">
                   <img ng-hide="message.nickname=='Server'" class="avatar" ng-src="https://pbs.twimg.com/profile_images/2077664484/avatar234_normal.png">

                   <div class="dm-info-message" ng-show="message.nickname=='Server'">
                       <div>
                           {{message.text}}
                       </div>
                   </div>

                   <div class="dm-content" ng-hide="message.nickname=='Server'">
                       <small class="time">
                            <span class="_timestamp">
                                {{message.time | date:'HH:mm:ss'}}
                            </span>
                           <div class="_timestamp" ng-show="message.nickname=='Server'">
                                {{message.text}}
                           </div>
                       </small>

                       <div class="dm-nickname" ng-class="{'sent':message.sent}">{{message.nickname}}</div>
                       <div class="dm-message" ng-class="{'sent':message.sent}">
                          <p>{{message.text
                              }}</p>
                           <div class="dm-caret">
                               <div class="dm-caret-outer"></div>
                               <div class="dm-caret-inner"></div>
                           </div>
                       </div>
                   </div>

               </div>
           </div>
       </div>
       <div id="panel-chat">
           <div id="message-content">
               <textarea  ng-model="message.text" placeholder="What's up?" required ng-enter="send()"></textarea>
           </div>
           <a class="panel-btn" id="send-msg-btn" ng-click="send()">
           <span class="glyphicon glyphicon-envelope"></span> Send message
       </a>
        </div>
   </div>
    <script type="text/javascript" src="interview/js/angular.js"></script>
    <script type="text/javascript" src="interview/js/angular-animate.js"></script>
    <script type="text/javascript" src="https://code.angularjs.org/1.2.18/angular-cookies.js"></script>
    <script type="text/javascript" src="interview/js/interviewApp.js"></script>
    <script type="text/javascript" src="interview/js/ngActivityIndicator.js"></script>
    <script type="text/javascript" src="interview/js/ui-bootstrap-tpls-0.11.0.js"></script>
    <script type="text/javascript" src="interview/components/header-menu/applicant/applicantHeaderCtrl.js" ></script>
    <script type="text/javascript" src="interview/components/questions/questionsCtrl.js"></script>
    <script type="text/javascript" src="interview/components/chat/chatCtrl.js"></script>
    <script type="text/javascript" src="interview/components/chat/chatSvc.js"></script>
    <script type="text/javascript" src="interview/components/questions/questionSvc.js"></script>
    <script type="text/javascript" src="interview/components/header-menu/timer/appointmentStartTimeSvc.js" ></script>
   <script type="text/javascript" src="interview/components/header-menu/applicant/applicantPhotoSvc.js" ></script>
    <script type="text/javascript" src="interview/components/header-menu/final-comment/finalComCtrl.js"></script>
   <script type="text/javascript" src="interview/components/header-menu/final-comment/finalComSvc.js"></script>

</body>
</html>
