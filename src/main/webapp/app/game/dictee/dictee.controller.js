(function() {
    'use strict';

    angular
        .module('jBaptisteApp')
        .controller('GameDicteeController', DicteeController);

    DicteeController.$inject = ['GameDictee', '$http'];

    function DicteeController(GameDictee, $http) {

        var vm = this;

        vm.dictees = GameDictee.query();
        vm.dictees.$promise.then(function(data) {
               console.log(data);
           });

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
//            var player = new MediaElementPlayer('audio-player', {
//                                //options
//                     });

            var audio = new Audio("/api/sound/play?word=" + vm.question.word);
            //"/api/sound/play/biberon.mp3");//
            audio.play();


//            $http.get("/api/sound/play?word=" + vm.question.word)
//                .then(function(response) {
//                    player.pause();
//                    player.setSrc(response.data);
//                    player.load();
//                    player.play();
//
//                    //var audio = new Audio(response.data);
//                    //audio.play();
//                });
//
            //var audio = new Audio('content/audio/words/' + vm.question.sound);
            //audio.play();
        };

        vm.nextWord = function() {

            vm.dictees = GameDictee.query();
            vm.dictees.$promise.then(function(data) {
                   console.log(data);
                    vm.question.answer = '';
                    vm.question.word = data.word;
                    vm.question.sound = data.word + ".mp3";
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
