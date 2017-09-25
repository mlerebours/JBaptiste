(function() {
    'use strict';
    angular
        .module('jBaptisteApp')
        .factory('Dictee', Dictee);

    Dictee.$inject = ['$resource'];

    function Dictee ($resource) {
        var resourceUrl =  'api/dictees/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
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
