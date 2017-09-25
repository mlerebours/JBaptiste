(function() {
    'use strict';

    angular
        .module('jBaptisteApp')
        .controller('GameDicteeController', DicteeController);

    DicteeController.$inject = ['Dictee'];

    function DicteeController(Dictee) {

        var vm = this;

        vm.dictees = [];

        vm.question = question;
        vm.points = 0;
        vm.total = 0;
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

        };

        vm.readWord = function() {
            var audio = new Audio('content/audio/words/' + vm.question.sound);
            audio.play();
        };

        vm.nextWord = function() {
            var word = words[Math.floor(Math.random()*words.length)];
            vm.question.answer = '';
            vm.question.word = word;
            vm.question.sound = word + ".mp3";
            vm.readWord();
        };

        vm.nextWord();
    };

    var question = {
        word: 'dire',
        sound: 'dire.mp3',
        answer: '',
        correct: false
    };

    var words = [
        'poire',
        'oiseau',
        'voiture',
        'poisson',
        'balançoire',
        'voisine',
        'étoile',
        'soir',
        'voici',
        'voilà',
        'bois',
        'roi',
        'moelle',
        'il voit',
        'noix',
        'noyau',
        'joyeux',
        'royaume',
        'wapiti',
        'boîte',
        'aquarium',
        'douane',
        'zouave',
        'poêle'
    ]

})();
