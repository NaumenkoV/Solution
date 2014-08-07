(function () {
    var questionModule = angular.module('questionMod', []);

    questionModule.controller('QuestionsController', ['$scope','$rootScope','Interview','User','Applicant','Appointment', function ($rootScope,$scope, Interview,User, Applicant, Appointment) {

        var userId = User.id;
        question.userId = userId;
        $scope.activeIndex = 0;

        var hasAnyOfMineQuestions = function(qs){
            for(var i=0;i<qs.length;i++){
                if(qs[i].userId===userId){
                    return true;
                }
            }
            return false;
        }
        var getMineQuestionIndex = function(qs){
            for(var i=0;i<qs.length;i++){
                if(qs[i].userId===userId){
                    return i;
                }
            }
            return 0;
        }

        var hardcodedQuestions = User.questions;
        if(hardcodedQuestions) {
            for (var i = 0; i < hardcodedQuestions.length; i++) {
                hardcodedQuestions[i].question = hardcodedQuestions[i].questionBody;
                delete hardcodedQuestions[i]['questionBody'];
                hardcodedQuestions[i].comment = "";
                hardcodedQuestions[i].mark = 1;
                if(!hardcodedQuestions[i].weight){
                    hardcodedQuestions[i].weight = 1;
                }
                hardcodedQuestions[i].id = null;
                hardcodedQuestions[i].userId = userId;
            }
        }
        else{
            hardcodedQuestions = null;
        }

        Interview.get(Appointment.appointmentId).then(function (response) {
                $scope.questions = response.data.questions;
                if(!$scope.questions){
                    $scope.questions = [];
                }
                if(hasAnyOfMineQuestions($scope.questions)){
                    $scope.activeIndex = getMineQuestionIndex($scope.questions)
                }
                else if(hardcodedQuestions){
                    $scope.questions = $scope.questions.concat(hardcodedQuestions);
                }
                else{
                    $scope.newQuestion();
                }
            },
            function (err) {
                var interview = {};
                interview.id = Appointment.appointmentId;
                interview.applicantId = Applicant.id;
                interview.usersId = Appointment.userIdList;

                interview.finalComment = "";
                interview.questions = hardcodedQuestions;

                Interview.add(interview).then(function(response){
                    $scope.questions = response.data.questions;
                    if(!$scope.questions){
                        $scope.questions = [];
                    }
                    if(hasAnyOfMineQuestions($scope.questions)){
                        $scope.activeIndex = getMineQuestionIndex($scope.questions)
                    }
                    else{
                        User.questions.concat(hardcodedQuestions);
                        $scope.newQuestion();
                    }
                },function(err){
                    window.location = "/sorry?code=4";
                });
            }
        );

        $scope.questionIsMine = function(q){
            return userId === q.userId;
        }

        $scope.updateInterview = function() {
            //remove all questions with no question definition
            for(var i=0;i<$scope.questions.length;i++){
                if(!$scope.questions[i].question){
                    $scope.questions.splice(i, 1);
                    i--;
                }
            }
            //
            if($scope.questions.length <= $scope.activeIndex){
                $scope.activeIndex = $scope.questions.length-1;
            }
            if($scope.activeIndex === -1){
                $scope.activeIndex = 0;
            }
            Interview.get(Appointment.appointmentId).then(function (response) {
                var qs = response.data.questions;
                //remove user questions
                for(var i=0; i<qs.length; i++){
                    if (qs[i].userId === userId){
                        qs.splice(i, 1);
                        i--;
                    }
                }
                //remove not mine questions
                for(var i=0; i < $scope.questions.length; i++){
                    if ($scope.questions[i].userId !== userId){
                        $scope.questions.splice(i, 1);
                        i--;
                    }
                }
                //concat
                $scope.questions = $scope.questions.concat(qs);

                response.data.questions = $scope.questions;
                Interview.edit(response.data).then(function(response){
                    $scope.questions = response.data.questions;
                    if(!hasAnyOfMineQuestions($scope.questions)){
                        $scope.newQuestion();
                    }
                    console.log("Interview was updated");
                });
            });
        }

        $scope.isActive = function(q){
            return q.question === $scope.questions[$scope.activeIndex].question;
        };
        $scope.isAnswered = function(q){
            return (q.comment) && !$scope.isActive(q);
        };
        $scope.selectQuestion = function(index){
            $scope.activeIndex = index;
            $scope.updateInterview();
        };

        $scope.intToWeight = function(num){
            var weights = ["low", "normal", "high", "critical"]
            return weights[num-1];
        };
        $scope.hoveringOver = function(value) {
            $scope.hoverMark = value;
        };
        $scope.newQuestion = function(){
            var last = $scope.questions.length-1;
            //if there is no questions yet
            if(last===-1){
                $scope.questions.push(angular.copy(question));
            }
            //only if the last question has question fomulation
            else if($scope.questions[last].question) {

                $scope.questions.push(angular.copy(question));
                $scope.activeIndex = $scope.questions.length - 1;
            }
        }
    }]);

    var question = {
        question: "",
        comment: "",
        mark: 0,
        id: null,
        weight: 2
    };
})();
