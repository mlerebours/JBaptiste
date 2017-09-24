(function() {
    'use strict';

    angular
        .module('jBaptisteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dictee', {
            parent: 'entity',
            url: '/dictee',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Dictees'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dictee/dictees.html',
                    controller: 'DicteeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('dictee-detail', {
            parent: 'dictee',
            url: '/dictee/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Dictee'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dictee/dictee-detail.html',
                    controller: 'DicteeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Dictee', function($stateParams, Dictee) {
                    return Dictee.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dictee',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dictee-detail.edit', {
            parent: 'dictee-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dictee/dictee-dialog.html',
                    controller: 'DicteeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dictee', function(Dictee) {
                            return Dictee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dictee.new', {
            parent: 'dictee',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dictee/dictee-dialog.html',
                    controller: 'DicteeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                dicteedate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dictee', null, { reload: 'dictee' });
                }, function() {
                    $state.go('dictee');
                });
            }]
        })
        .state('dictee.edit', {
            parent: 'dictee',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dictee/dictee-dialog.html',
                    controller: 'DicteeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dictee', function(Dictee) {
                            return Dictee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dictee', null, { reload: 'dictee' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dictee.delete', {
            parent: 'dictee',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dictee/dictee-delete-dialog.html',
                    controller: 'DicteeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Dictee', function(Dictee) {
                            return Dictee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dictee', null, { reload: 'dictee' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
