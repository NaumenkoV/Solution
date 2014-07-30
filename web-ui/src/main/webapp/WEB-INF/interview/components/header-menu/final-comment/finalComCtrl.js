var  mediapp = angular.module('finalComMod',[]);
mediapp.controller('finalComCtrl',['$scope','Comment', 'User', function($scope, Comment, User){

        $scope.user = User;
        $scope.finalComment = '';



    $scope.submitComment = function(){
        var finalComment = {};
        finalComment.finalComment = $scope.finalComment;
//        Comment.update(finalComment).then(function(response){
//                //yoohoo, you completed interview!
                alert("You're about to leave interview. Thanks!");
                window.location = "/";
//        },function(err){
//                alert("Can't send final comment. Service is unreachable");
//            }
//        );
    };
}]);
