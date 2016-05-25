(function() {
    'use strict';

    angular
        .module('kcotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pozvani', {
            parent: 'entity',
            url: '/pozvani?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pozvanis'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pozvani/pozvanis.html',
                    controller: 'PozvaniController',
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
        .state('pozvani-detail', {
            parent: 'entity',
            url: '/pozvani/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pozvani'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pozvani/pozvani-detail.html',
                    controller: 'PozvaniDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Pozvani', function($stateParams, Pozvani) {
                    return Pozvani.get({id : $stateParams.id});
                }]
            }
        })
        .state('pozvani.new', {
            parent: 'pozvani',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pozvani/pozvani-dialog.html',
                    controller: 'PozvaniDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pozvani', null, { reload: true });
                }, function() {
                    $state.go('pozvani');
                });
            }]
        })
        .state('pozvani.edit', {
            parent: 'pozvani',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pozvani/pozvani-dialog.html',
                    controller: 'PozvaniDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pozvani', function(Pozvani) {
                            return Pozvani.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('pozvani', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pozvani.delete', {
            parent: 'pozvani',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pozvani/pozvani-delete-dialog.html',
                    controller: 'PozvaniDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pozvani', function(Pozvani) {
                            return Pozvani.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('pozvani', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
