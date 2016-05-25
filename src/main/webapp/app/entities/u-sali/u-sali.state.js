(function() {
    'use strict';

    angular
        .module('kcotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('u-sali', {
            parent: 'entity',
            url: '/u-sali?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'USalis'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/u-sali/u-salis.html',
                    controller: 'USaliController',
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
        .state('u-sali-detail', {
            parent: 'entity',
            url: '/u-sali/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'USali'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/u-sali/u-sali-detail.html',
                    controller: 'USaliDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'USali', function($stateParams, USali) {
                    return USali.get({id : $stateParams.id});
                }]
            }
        })
        .state('u-sali.new', {
            parent: 'u-sali',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/u-sali/u-sali-dialog.html',
                    controller: 'USaliDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                naziv: null,
                                datum: null,
                                poslovna_godina: null,
                                kontakt_ime: null,
                                kontakt_prz: null,
                                kontakt_broj: null,
                                kontakt_email: null,
                                status: null,
                                zarada: null,
                                prihod: null,
                                procenat: null,
                                posecenost: null,
                                titl: null,
                                oprema: null,
                                napomene: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('u-sali', null, { reload: true });
                }, function() {
                    $state.go('u-sali');
                });
            }]
        })
        .state('u-sali.edit', {
            parent: 'u-sali',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/u-sali/u-sali-dialog.html',
                    controller: 'USaliDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['USali', function(USali) {
                            return USali.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('u-sali', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('u-sali.delete', {
            parent: 'u-sali',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/u-sali/u-sali-delete-dialog.html',
                    controller: 'USaliDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['USali', function(USali) {
                            return USali.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('u-sali', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
