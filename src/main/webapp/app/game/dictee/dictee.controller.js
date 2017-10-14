(function() {
    'use strict';

    angular
        .module('jBaptisteApp')
        .controller('GameDicteeController', DicteeController);

    DicteeController.$inject = ['GameDictee', 'Dictee', '$http'];

    function DicteeController(GameDictee, Dictee, $http) {

        var vm = this;

        vm.dicteeId = 0;

        loadAll();

        function loadAll() {
            Dictee.query(function(result) {
                vm.dictees = result;
                vm.dicteeId = vm.dictees[vm.dictees.length -1].id;
                vm.searchQuery = null;
                vm.loadDictee();
            });
        }

        vm.loadDictee = function() {
            console.log("dictee id = " + vm.dicteeId);
            vm.nextWord();
        };


        vm.question = question;
        vm.points = 0;
        vm.total = 0;
        vm.nb_questions = 10;
        vm.finished = false;

        vm.restart = function() {
            vm.total = 0;
            vm.points = 0;
            vm.nextWord();
            vm.finished = false;
        };

        vm.checkAnswer = function() {
            if (vm.question.answer === '') {
                vm.readWord();
                return;
            }

            //vm.question.answer = vm.question.word.toUpperCase() + " " + vm.question.answer.toUpperCase();
            vm.question.correct = vm.question.word.toUpperCase() === vm.question.answer.toUpperCase();

            vm.total += 1;
            vm.finished = vm.total == vm.nb_questions;

            if (vm.question.correct) {
                vm.points += 1;
                if(!vm.finished)
                    vm.nextWord();
            }
            else {
                if(!vm.finished)
                    vm.readWord();
            }

        };

        vm.readWord = function() {
            if (vm.question.word) {
                var audio = new Audio("api/sound/play?file=" + vm.question.sound);
                audio.play();
            }
        };

        vm.nextWord = function() {

            GameDictee.get(vm.dicteeId, function(data) {
                   console.log(data);
                    vm.question.answer = '';
                    vm.question.word = data.word;
                    vm.question.sound = data.soundfile;
                    vm.readWord();
                   });
        };

        vm.nextWord();
    };

    var question = {
        word: 'dire',
        sound: 'dire.mp3',
        answer: '',
        correct: false
    };

})();
