(function() {
    'use strict';

    angular
        .module('kcotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sediste', {
            parent: 'entity',
            url: '/sediste?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Sedistes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sediste/sedistes.html',
                    controller: 'SedisteController',
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
        .state('sediste-detail', {
            parent: 'entity',
            url: '/sediste/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Sediste'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sediste/sediste-detail.html',
                    controller: 'SedisteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Sediste', function($stateParams, Sediste) {
                    return Sediste.get({id : $stateParams.id});
                }]
            }
        })
        .state('sediste.new', {
            parent: 'sediste',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sediste/sediste-dialog.html',
                    controller: 'SedisteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                red: null,
                                broj: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sediste', null, { reload: true });
                }, function() {
                    $state.go('sediste');
                });
            }]
        })
        .state('sediste.edit', {
            parent: 'sediste',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sediste/sediste-dialog.html',
                    controller: 'SedisteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sediste', function(Sediste) {
                            return Sediste.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('sediste', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sediste.delete', {
            parent: 'sediste',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sediste/sediste-delete-dialog.html',
                    controller: 'SedisteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Sediste', function(Sediste) {
                            return Sediste.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('sediste', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
