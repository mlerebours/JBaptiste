(function() {
    'use strict';

    angular
        .module('jBaptisteApp')
        .filter('secondsToDateTime', secondsToDateTime);

    function secondsToDateTime() {
        return secondsToDateTimeFilter;

        function secondsToDateTimeFilter (seconds) {
            return new Date(1970, 0, 1).setSeconds(seconds);
        }
    }
})();
