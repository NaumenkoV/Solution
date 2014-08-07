var  mediapp = angular.module('applicantPopupModule',['ngActivityIndicator']);
mediapp.controller('applicantPopupCtrl',['$rootScope','$scope','$modalInstance','Applicant','Repo' ,'$activityIndicator', '$timeout', function($rootScope, $scope,$modalInstance,Applicant,Repo, $activityIndicator,$timeout){




                $modalInstance.result.then(function () {}, function () {  $scope.sys.stopVideo();});

    $scope.isWebCamAsk=false;
    $scope.applicant = Applicant;
    $activityIndicator.startAnimating();

    Repo.getPhoto().then(function(response){
        $scope.defPhoto =  response.data;
        $activityIndicator.stopAnimating();
    },
        function(response){
        $scope.defPhoto =  'interview/img/maestro-joda.jpg';
            $activityIndicator.stopAnimating();
    })

    $scope.MEDIA_HOLDER = "IMG";
    $scope.sys = {video:"",localMediaStream:""};

    Repo.getCV().then(function(response){
            $scope.cvTitle = 'Link to cv';
            $scope.cv =  '/repository/doc/'+Applicant.id;
        },
        function(response){
            $scope.cvTitle = 'Not uploaded yet';
            $scope.cv =  '#';
        })


    $scope.sys.stopVideo = function(){

        if($scope.sys.localMediaStream){
            $scope.sys.localMediaStream.stop();
            $scope.sys.localMediaStream = null;
        }


    };





  /*  $scope.user = User.get().then(function(data) {
   return data;
   });


 $activityIndicator.startAnimating();

  Applicant.get().then(function(data) {
        $scope.applicant = data;
        $activityIndicator.stopAnimating();

    });

   $timeout(function () {
        $activityIndicator.stopAnimating();
    }, 3000);
*/


  $scope.selectDiv = function(mediaHolder){
        $scope.MEDIA_HOLDER = mediaHolder;
    }

$scope.postPhoto = function(){
    Repo.addPhoto($scope.sys.tmpPhoto).then(function(response){
        $scope.defPhoto = $scope.sys.tmpPhoto;
    });
}
  $scope.sys.putVideo = function (elem) {
      $scope.isWebCamAsk = true;

        var videoObj = {"video": true };

        if (navigator.getUserMedia) { // Standard
            navigator.getUserMedia(videoObj, function (stream) {
                elem.src = stream;
                $scope.$apply( function(){
                    $scope.sys.localMediaStream = stream;
                });
                elem.play();
            }, errBack);
        } else if (navigator.webkitGetUserMedia) { // WebKit-prefixed
            navigator.webkitGetUserMedia(videoObj, function (stream) {
                elem.src = window.webkitURL.createObjectURL(stream);
                $scope.$apply(function(){
                    $scope.sys.localMediaStream = stream;
                });
                elem.play();
            }, errBack);
        }
        else if (navigator.mozGetUserMedia) { // Firefox-prefixed
            navigator.mozGetUserMedia(videoObj, function (stream) {
                elem.src = window.URL.createObjectURL(stream);
                $scope.$apply( function(){
                    $scope.sys.localMediaStream = stream;
                });
                elem.play();
            }, errBack);
        }
    };



    errBack = function (error) {
        alert("Cant find web camera on your device");
        console.log("Video capture error: ", error.code);
    };



}]);



 mediapp.directive('imageHolder',function(){
         return {
             restrict: "A",
             scope:true,
             link: function (scope, element, attrs) {



             }
         };
     }
 );

mediapp.directive('videoHolder', function(){
    return {
        restrict: "A",
        scope:false,
        link: function (scope, element, attrs) {
           scope.sys.putVideo(element[0]);
           scope.sys.video = element[0];

        }
    };
}
);


 mediapp.directive('resultHolder', function(){
         return {
             restrict: "A",
             scope:false,
             link: function (scope, element, attrs) {

                 var canvas = document.createElement("canvas");
                 canvas.width = 640;
                 canvas.height = 480;
                 context = canvas.getContext('2d');
                 context.drawImage(scope.sys.video,0,0);

                 if(scope.sys.localMediaStream){
                     scope.sys.localMediaStream.stop();
                     scope.sys.localMediaStream = null;
                 }

                 scope.sys.tmpPhoto = canvas.toDataURL();
             }
         };
     }
 );








