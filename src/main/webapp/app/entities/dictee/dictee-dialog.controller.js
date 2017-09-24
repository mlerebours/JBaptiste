(function() {
    'use strict';

    angular
        .module('jBaptisteApp')
        .controller('DicteeDialogController', DicteeDialogController);

    DicteeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Dictee', 'Question'];

    function DicteeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Dictee, Question) {
        var vm = this;

        vm.dictee = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.questions = Question.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.dictee.id !== null) {
                Dictee.update(vm.dictee, onSaveSuccess, onSaveError);
            } else {
                Dictee.save(vm.dictee, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jBaptisteApp:dicteeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dicteedate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
