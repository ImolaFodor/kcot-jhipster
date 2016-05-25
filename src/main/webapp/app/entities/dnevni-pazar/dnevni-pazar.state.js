(function() {
    'use strict';

    angular
        .module('kcotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dnevni-pazar', {
            parent: 'entity',
            url: '/dnevni-pazar?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DnevniPazars'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dnevni-pazar/dnevni-pazars.html',
                    controller: 'DnevniPazarController',
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
        .state('dnevni-pazar-detail', {
            parent: 'entity',
            url: '/dnevni-pazar/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DnevniPazar'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dnevni-pazar/dnevni-pazar-detail.html',
                    controller: 'DnevniPazarDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'DnevniPazar', function($stateParams, DnevniPazar) {
                    return DnevniPazar.get({id : $stateParams.id});
                }]
            }
        })
        .state('dnevni-pazar.new', {
            parent: 'dnevni-pazar',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dnevni-pazar/dnevni-pazar-dialog.html',
                    controller: 'DnevniPazarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id_prodatog: null,
                                iznos: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dnevni-pazar', null, { reload: true });
                }, function() {
                    $state.go('dnevni-pazar');
                });
            }]
        })
        .state('dnevni-pazar.edit', {
            parent: 'dnevni-pazar',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dnevni-pazar/dnevni-pazar-dialog.html',
                    controller: 'DnevniPazarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DnevniPazar', function(DnevniPazar) {
                            return DnevniPazar.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('dnevni-pazar', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dnevni-pazar.delete', {
            parent: 'dnevni-pazar',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dnevni-pazar/dnevni-pazar-delete-dialog.html',
                    controller: 'DnevniPazarDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DnevniPazar', function(DnevniPazar) {
                            return DnevniPazar.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('dnevni-pazar', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
