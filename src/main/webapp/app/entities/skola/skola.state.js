(function() {
    'use strict';

    angular
        .module('kcotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('skola', {
            parent: 'entity',
            url: '/skola?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Skolas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/skola/skolas.html',
                    controller: 'SkolaController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('skola-detail', {
            parent: 'entity',
            url: '/skola/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Skola'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/skola/skola-detail.html',
                    controller: 'SkolaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Skola', function($stateParams, Skola) {
                    return Skola.get({id : $stateParams.id});
                }]
            }
        })
        .state('skola.new', {
            parent: 'skola',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/skola/skola-dialog.html',
                    controller: 'SkolaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                naziv: null,
                                kontakt_ime: null,
                                kontakt_prz: null,
                                kontakt_broj: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('skola', null, { reload: true });
                }, function() {
                    $state.go('skola');
                });
            }]
        })
        .state('skola.edit', {
            parent: 'skola',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/skola/skola-dialog.html',
                    controller: 'SkolaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Skola', function(Skola) {
                            return Skola.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('skola', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('skola.delete', {
            parent: 'skola',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/skola/skola-delete-dialog.html',
                    controller: 'SkolaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Skola', function(Skola) {
                            return Skola.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('skola', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
