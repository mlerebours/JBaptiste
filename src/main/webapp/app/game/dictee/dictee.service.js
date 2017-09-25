(function() {
    'use strict';
    angular
        .module('jBaptisteApp')
        .factory('GameDictee', GameDictee);

    GameDictee.$inject = ['$resource'];

    function GameDictee ($resource) {
        var resourceUrl =  'api/game/dictee/question?dicteeId=1';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: false},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
