angular.module('timerMod',[]).factory('StartTimeUpdate',['$http','Appointment', function($http, Appointment) {
    return {
        update: function(){
            return $http.put("http://localhost:8080/appointments/",Appointment).then(function(response){
                return response.data;
            });
        }
    }
}]);
