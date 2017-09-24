(function() {
    'use strict';

    angular
        .module('jBaptisteApp')
        .controller('DicteeDetailController', DicteeDetailController);

    DicteeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Dictee', 'Question'];

    function DicteeDetailController($scope, $rootScope, $stateParams, previousState, entity, Dictee, Question) {
        var vm = this;

        vm.dictee = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jBaptisteApp:dicteeUpdate', function(event, result) {
            vm.dictee = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
