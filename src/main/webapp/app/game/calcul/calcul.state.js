(function() {
    'use strict';

    angular
        .module('jBaptisteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('playcalcul', {
            parent: 'game',
            url: '/playcalcul',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Calcul'
            },
            views: {
                'content@': {
                    templateUrl: 'app/game/calcul/calcul.html',
                    controller: 'GameCalculController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
    }

})();
