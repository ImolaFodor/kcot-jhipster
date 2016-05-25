(function() {
    'use strict';

    angular
        .module('kcotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('izlozena-dela', {
            parent: 'entity',
            url: '/izlozena-dela?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Izlozena_delas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/izlozena-dela/izlozena-delas.html',
                    controller: 'Izlozena_delaController',
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
        .state('izlozena-dela-detail', {
            parent: 'entity',
            url: '/izlozena-dela/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Izlozena_dela'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/izlozena-dela/izlozena-dela-detail.html',
                    controller: 'Izlozena_delaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Izlozena_dela', function($stateParams, Izlozena_dela) {
                    return Izlozena_dela.get({id : $stateParams.id});
                }]
            }
        })
        .state('izlozena-dela.new', {
            parent: 'izlozena-dela',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/izlozena-dela/izlozena-dela-dialog.html',
                    controller: 'Izlozena_delaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                naziv: null,
                                opis: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('izlozena-dela', null, { reload: true });
                }, function() {
                    $state.go('izlozena-dela');
                });
            }]
        })
        .state('izlozena-dela.edit', {
            parent: 'izlozena-dela',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/izlozena-dela/izlozena-dela-dialog.html',
                    controller: 'Izlozena_delaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Izlozena_dela', function(Izlozena_dela) {
                            return Izlozena_dela.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('izlozena-dela', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('izlozena-dela.delete', {
            parent: 'izlozena-dela',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/izlozena-dela/izlozena-dela-delete-dialog.html',
                    controller: 'Izlozena_delaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Izlozena_dela', function(Izlozena_dela) {
                            return Izlozena_dela.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('izlozena-dela', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
