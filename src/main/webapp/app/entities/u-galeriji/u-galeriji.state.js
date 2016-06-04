(function() {
    'use strict';

    angular
        .module('kcotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('u-galeriji', {
            parent: 'entity',
            url: '/u-galeriji?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'U galeriji'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/u-galeriji/u-galerijis.html',
                    controller: 'UGalerijiController',
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
        .state('u-galeriji-detail', {
            parent: 'entity',
            url: '/u-galeriji/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UGaleriji'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/u-galeriji/u-galeriji-detail.html',
                    controller: 'UGalerijiDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UGaleriji', function($stateParams, UGaleriji) {
                    return UGaleriji.get({id : $stateParams.id});
                }]
            }
        })
        .state('u-galeriji.new', {
            parent: 'u-galeriji',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/u-galeriji/u-galeriji-dialog.html',
                    controller: 'UGalerijiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                naziv: null,
                                poslovna_godina: null,
                                kontakt_ime: null,
                                kontakt_prz: null,
                                kontakt_broj: null,
                                kontakt_email: null,
                                moderator_ime: null,
                                moderator_prz: null,
                                moderator_broj: null,
                                trosak: null,
                                unajmio_ime: null,
                                unajmio_prz: null,
                                unajmio_email: null,
                                br_fakture: null,
                                zarada: null,
                                posecenost: null,
                                tip: null,
                                napomene: null,
                                datum: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('u-galeriji', null, { reload: true });
                }, function() {
                    $state.go('u-galeriji');
                });
            }]
        })
        .state('u-galeriji.edit', {
            parent: 'u-galeriji',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/u-galeriji/u-galeriji-dialog.html',
                    controller: 'UGalerijiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UGaleriji', function(UGaleriji) {
                            return UGaleriji.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('u-galeriji', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('u-galeriji.delete', {
            parent: 'u-galeriji',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/u-galeriji/u-galeriji-delete-dialog.html',
                    controller: 'UGalerijiDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UGaleriji', function(UGaleriji) {
                            return UGaleriji.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('u-galeriji', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
