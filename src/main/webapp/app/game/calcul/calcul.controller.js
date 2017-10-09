(function() {
    'use striact';

    angular
        .module('jBaptisteApp')
        .controller('GameCalculController', CalculController);

    CalculController.$inject = [];

    function CalculController() {

        var vm = this;

        vm.calculId = Math.floor(Math.random() * 10);
        loadAll();

        function loadAll() {
            vm.calculs = [];
            for (var i = 1; i < 11; i++) {
               vm.calculs.push({id: i, name: i, selected: true});
            }
        }

        vm.loadCalcul = function() {
            console.log("calcul id = " + vm.calculId);
            vm.nextWord();
        };

        vm.question = question;
        vm.points = 0;
        vm.total = 0;
        vm.finished = false;
        var d = new Date();
        vm.start = d.getTime();
        vm.time = 0;
        vm.operation = "+";

        vm.restart = function() {
            vm.total = 0;
            vm.points = 0;
            vm.nextWord();
            vm.finished = false;
            var d = new Date();
            vm.start = d.getTime();
        };

        vm.checkAnswer = function() {
            if (vm.question.answer === '') {
                return;
            }

            var result = 0;
            if (vm.operation === "+") {
                result = vm.question.num1 + vm.question.num2;
            } else if (vm.operation === "-") {
                result = vm.question.num1 - vm.question.num2;
            } else if (vm.operation === "x") {
                result = vm.question.num1 * vm.question.num2;
            }

            vm.question.correct = result == vm.question.answer;

            vm.total += 1;
            vm.finished = vm.total === 10;

            if (vm.question.correct) {
                vm.points += 1;
                if(!vm.finished)
                    vm.nextWord();
            }
            else {
                if(!vm.finished)
                    vm.readWord();
            }

           var d = new Date();
           vm.time = (d.getTime() - vm.start) / 1000;

        };

        vm.nextWord = function() {
            var filtre = vm.calculs.filter(function(calc) {
                return calc.selected;
            });

            var num1 = filtre[Math.floor(Math.random()*filtre.length)].id;
            var num2 = Math.floor(Math.random() * 13);

            vm.question.num1 = num1;
            vm.question.num2 = num2;
            if (vm.operation === "-") {
                vm.question.num1 = Math.max(num1, num2);
                vm.question.num2 = Math.min(num1, num2);
            }
            vm.question.answer = '';
            console.log(vm.question);
        };

        vm.nextWord();
    };

    var question = {
        num1: 4,
        num2: 3,
        answer: '',
        correct: false
    };

})();
