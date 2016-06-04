(function() {
    'use strict';

    angular
        .module('kcotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gost', {
            parent: 'entity',
            url: '/gost?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Gosti'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gost/gosts.html',
                    controller: 'GostController',
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
        .state('gost-detail', {
            parent: 'entity',
            url: '/gost/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Gost'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gost/gost-detail.html',
                    controller: 'GostDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Gost', function($stateParams, Gost) {
                    return Gost.get({id : $stateParams.id});
                }]
            }
        })
        .state('gost.new', {
            parent: 'gost',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gost/gost-dialog.html',
                    controller: 'GostDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ime: null,
                                prz: null,
                                broj: null,
                                email: null,
                                adresa: null,
                                tip_gost: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gost', null, { reload: true });
                }, function() {
                    $state.go('gost');
                });
            }]
        })
        .state('gost.edit', {
            parent: 'gost',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gost/gost-dialog.html',
                    controller: 'GostDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gost', function(Gost) {
                            return Gost.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('gost', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gost.delete', {
            parent: 'gost',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gost/gost-delete-dialog.html',
                    controller: 'GostDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Gost', function(Gost) {
                            return Gost.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('gost', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
