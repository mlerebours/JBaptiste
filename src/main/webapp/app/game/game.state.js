(function() {
    'use strict';

    angular
        .module('jBaptisteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('game', {
            abstract: true,
            parent: 'app'
        });
    }
})();
