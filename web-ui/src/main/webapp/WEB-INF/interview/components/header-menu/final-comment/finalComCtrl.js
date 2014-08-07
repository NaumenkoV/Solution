var  mediapp = angular.module('finalComMod',[]);
mediapp.controller('finalComCtrl',['$scope','Comment', 'User', 'Appointment', 'Interview', '$rootScope','$http', function($scope, Comment, User, Appointment,Interview, $rootScope, $http){

        $scope.user = User;
    Interview.get(Appointment.appointmentId).then(function(response){
        $scope.finalComment = response.data.finalComment;
    })


    var applicantId = Appointment.applicantId;
    var baseUrl = "http://176.36.11.25:8080/";
    var $cookies = angular.injector(['ngCookies']).get('$cookies');
    var groupID = $cookies.groupID;
    var list = {};
list[applicantId] = {"status":"NOT_PASSED", "rank":-1};

    $scope.submitComment = function(){
        console.log(list);
        $http({method: 'PUT',
               url: baseUrl+'groups/' + groupID + '/applicants',
               data: JSON.stringify(list)
                 }).then(function(){
            console.log("applicant updated");
        },function(err){

        });


        Comment.update($scope.finalComment).then(function(response){
                //yoohoo, you completed interview!
                if(!$scope.finalComment){
                    alert("You're about to leave interview. But you haven't left any final comment! Please write one");
                }
                else {
                    alert("You're about to leave interview. Thanks!");
                    //TODO:
                    $rootScope.$broadcast('Final Comment was submit');
                    window.location = "/";
                }
        },function(err){
                alert("Can't send final comment. Service is unreachable");
            }

        );
    };
}]);
