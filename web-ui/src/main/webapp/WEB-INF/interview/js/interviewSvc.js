angular.module('interviewApp').factory('Interview',['$http','Applicant', function($http, Applicant) {
    var baseUrl = "http://176.36.11.25:8080/interviews"
    return {
        add: function(interview){
            return $http({method: 'POST', url: baseUrl+"/", data: interview});
        },
        edit: function(interview){
            return $http({method: 'PUT', url: baseUrl+"/", data: interview});
        },
        getAll: function(){
            return $http({method: 'GET', url: baseUrl+"/"})
        },
        get: function(interviewId){
            return $http({method: 'GET', url: baseUrl+"/" + interviewId})
        },
        delete: function(interviewId){
            return $http({method: 'DELETE', url: baseUrl+"/" + interviewId})
        }
    }
}])
