angular.module('finalComMod').factory('Comment',['$http','Appointment', function($http, Appointment) {
    return {
        update: function(finalComment){
            finalComment.interviewId = Appointment.appointmentId;
            if(!finalComment.bonusPoints){
                finalComment.bonusPoints = 0;
            }
            return $http({method: 'PUT', url: "/interviewing/final_comment/", data : finalComment});
        }
    }}])
