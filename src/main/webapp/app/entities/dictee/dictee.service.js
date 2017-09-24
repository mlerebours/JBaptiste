(function() {
    'use strict';
    angular
        .module('jBaptisteApp')
        .factory('Dictee', Dictee);

    Dictee.$inject = ['$resource', 'DateUtils'];

    function Dictee ($resource, DateUtils) {
        var resourceUrl =  'api/dictees/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dicteedate = DateUtils.convertLocalDateFromServer(data.dicteedate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dicteedate = DateUtils.convertLocalDateToServer(copy.dicteedate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dicteedate = DateUtils.convertLocalDateToServer(copy.dicteedate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
