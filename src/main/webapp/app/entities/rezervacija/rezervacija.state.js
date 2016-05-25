(function() {
    'use strict';

    angular
        .module('kcotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rezervacija', {
            parent: 'entity',
            url: '/rezervacija?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Rezervacijas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rezervacija/rezervacijas.html',
                    controller: 'RezervacijaController',
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
        .state('rezervacija-detail', {
            parent: 'entity',
            url: '/rezervacija/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Rezervacija'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rezervacija/rezervacija-detail.html',
                    controller: 'RezervacijaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Rezervacija', function($stateParams, Rezervacija) {
                    return Rezervacija.get({id : $stateParams.id});
                }]
            }
        })
        .state('rezervacija.new', {
            parent: 'rezervacija',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rezervacija/rezervacija-dialog.html',
                    controller: 'RezervacijaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ups: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rezervacija', null, { reload: true });
                }, function() {
                    $state.go('rezervacija');
                });
            }]
        })
        .state('rezervacija.edit', {
            parent: 'rezervacija',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rezervacija/rezervacija-dialog.html',
                    controller: 'RezervacijaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rezervacija', function(Rezervacija) {
                            return Rezervacija.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('rezervacija', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rezervacija.delete', {
            parent: 'rezervacija',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rezervacija/rezervacija-delete-dialog.html',
                    controller: 'RezervacijaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Rezervacija', function(Rezervacija) {
                            return Rezervacija.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('rezervacija', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
