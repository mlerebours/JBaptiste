(function() {
    'use strict';

    angular
        .module('jBaptisteApp')
        .controller('DicteeDeleteController',DicteeDeleteController);

    DicteeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Dictee'];

    function DicteeDeleteController($uibModalInstance, entity, Dictee) {
        var vm = this;

        vm.dictee = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Dictee.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
