(function() {
    'use strict';

    angular
        .module('jBaptisteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('playdictee', {
            parent: 'game',
            url: '/playdictee',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Dictee'
            },
            views: {
                'content@': {
                    templateUrl: 'app/game/dictee/dictee.html',
                    controller: 'GameDicteeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
    }

})();
