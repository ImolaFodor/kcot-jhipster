(function() {
    'use strict';

    angular
        .module('kcotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('donirana-dela', {
            parent: 'entity',
            url: '/donirana-dela?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Donirana dela'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/donirana-dela/donirana-delas.html',
                    controller: 'Donirana_delaController',
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
        .state('donirana-dela-detail', {
            parent: 'entity',
            url: '/donirana-dela/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Donirana_dela'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/donirana-dela/donirana-dela-detail.html',
                    controller: 'Donirana_delaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Donirana_dela', function($stateParams, Donirana_dela) {
                    return Donirana_dela.get({id : $stateParams.id});
                }]
            }
        })
        .state('donirana-dela.new', {
            parent: 'donirana-dela',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/donirana-dela/donirana-dela-dialog.html',
                    controller: 'Donirana_delaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                naziv: null,
                                br_ugovora: null,
                                opis: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('donirana-dela', null, { reload: true });
                }, function() {
                    $state.go('donirana-dela');
                });
            }]
        })
        .state('donirana-dela.edit', {
            parent: 'donirana-dela',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/donirana-dela/donirana-dela-dialog.html',
                    controller: 'Donirana_delaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Donirana_dela', function(Donirana_dela) {
                            return Donirana_dela.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('donirana-dela', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('donirana-dela.delete', {
            parent: 'donirana-dela',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/donirana-dela/donirana-dela-delete-dialog.html',
                    controller: 'Donirana_delaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Donirana_dela', function(Donirana_dela) {
                            return Donirana_dela.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('donirana-dela', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
