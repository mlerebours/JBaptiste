(function() {
    'use strict';

    angular
        .module('jBaptisteApp')
        .controller('DicteeController', DicteeController);

    DicteeController.$inject = ['Dictee'];

    function DicteeController(Dictee) {

        var vm = this;

        vm.dictees = [];

        loadAll();

        function loadAll() {
            Dictee.query(function(result) {
                vm.dictees = result;
                vm.searchQuery = null;
            });
        }
    }
})();
