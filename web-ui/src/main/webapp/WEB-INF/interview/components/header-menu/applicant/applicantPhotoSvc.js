angular.module('applicantPopupModule').factory('Repo',['$http','Applicant', function($http, Applicant) {
    var baseUrl = "http://176.36.11.25:8080/"
    return {
        addPhoto: function(photo){
           return $http({method: 'POST', url: baseUrl+"repository/img/applicant/" + Applicant.id, data : photo});
        },
        getPhoto: function(){
            return $http({method: 'GET', url: baseUrl+"repository/img/applicant/" + Applicant.id})
        },
        getCV: function(){
            return $http({method: 'GET', url: baseUrl+"repository/doc/" + Applicant.id})
        }
    }
}])
