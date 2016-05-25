(function() {
    'use strict';

    angular
        .module('kcotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('umetnicko-delo', {
            parent: 'entity',
            url: '/umetnicko-delo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UmetnickoDelos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/umetnicko-delo/umetnicko-delos.html',
                    controller: 'UmetnickoDeloController',
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
        .state('umetnicko-delo-detail', {
            parent: 'entity',
            url: '/umetnicko-delo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UmetnickoDelo'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/umetnicko-delo/umetnicko-delo-detail.html',
                    controller: 'UmetnickoDeloDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UmetnickoDelo', function($stateParams, UmetnickoDelo) {
                    return UmetnickoDelo.get({id : $stateParams.id});
                }]
            }
        })
        .state('umetnicko-delo.new', {
            parent: 'umetnicko-delo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/umetnicko-delo/umetnicko-delo-dialog.html',
                    controller: 'UmetnickoDeloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                naziv: null,
                                umetnik_ime: null,
                                umetnik_prz: null,
                                inventarski_broj: null,
                                tip_um_dela: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('umetnicko-delo', null, { reload: true });
                }, function() {
                    $state.go('umetnicko-delo');
                });
            }]
        })
        .state('umetnicko-delo.edit', {
            parent: 'umetnicko-delo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/umetnicko-delo/umetnicko-delo-dialog.html',
                    controller: 'UmetnickoDeloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UmetnickoDelo', function(UmetnickoDelo) {
                            return UmetnickoDelo.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('umetnicko-delo', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('umetnicko-delo.delete', {
            parent: 'umetnicko-delo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/umetnicko-delo/umetnicko-delo-delete-dialog.html',
                    controller: 'UmetnickoDeloDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UmetnickoDelo', function(UmetnickoDelo) {
                            return UmetnickoDelo.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('umetnicko-delo', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
