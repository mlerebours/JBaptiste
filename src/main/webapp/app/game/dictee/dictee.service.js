(function() {
    'use strict';
    angular
        .module('jBaptisteApp')
        .factory('GameDictee', GameDictee);

    GameDictee.$inject = ['$http'];

    function GameDictee ($http) {
        var resourceUrl =  'api/game/dictee/question?dicteeId=';

        return {
            get: function(dictee_id, callback) {
              $http.get(resourceUrl + dictee_id).
                success(function(data, status) {
                  callback(data, status);
                }).
                error(function(error, status) {
                  callback(error, status);
                });
            }
          };


//        load_dictee = function(dicteeId) {
//          // $http returns a promise, which has a then function, which also returns a promise
//          var promise = $http.get(resourceUrl + dicteeId).then(function (response) {
//            // The then function here is an opportunity to modify the response
//            console.log(response);
//            // The return value gets picked up by the then in the controller.
//            return response.data;
//          });
//          // Return the promise to the controller
//          return promise;
//        };



//        load_dictee: function(dicteeId) {
//            $http.get(resourceUrl + dicteeId)
//                .then(function(response) {
//                    dictee = response.data;
//                });
//        };

        //return this;

//        return $resource(resourceUrl, {}, {
//            'query': { method: 'GET', isArray: false},
//            'get': {
//                method: 'GET',
//                transformResponse: function (data) {
//                    if (data) {
//                        data = angular.fromJson(data);
//                    }
//                    return data;
//                }
//            },
//            'update': { method:'PUT' }
//        });
    }
})();
