angular.module('questionMod').factory('Question',['$http','Appointment', function($http, Appointment) {
    return {
        update: function(q){
            return $http({method: 'PUT', url: "/interviews/interviewing/answer/", data : q});
        },
        add: function(q){
            if(!q.answer){
                q.answer = "";
            }
            if(!q.comment){
                q.comment = "";
            }
            if(!q.mark){
                q.mark = 0;
            }
            if(!q.id){
                q.id = "";
            }
            q.interviewId = Appointment.appointmentId;
            return $http({method: 'POST', url: "/interviews/interviewing/answer/", data : q});
        }
    }
}])
