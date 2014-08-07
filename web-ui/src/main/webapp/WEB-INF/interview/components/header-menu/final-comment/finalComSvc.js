angular.module('finalComMod').factory('Comment',['$http','Appointment', function($http, Appointment) {
    var baseUrl = "http://176.36.11.25:8080/interviews";
    return {
        update: function(finalComment){
            return $http({method: 'GET', url: baseUrl+"/" + Appointment.appointmentId}).then(function(response){
                response.data.finalComment = finalComment;
                $http({method: 'PUT', url: baseUrl+"/", data: response.data});
            })
        }
    }}])
