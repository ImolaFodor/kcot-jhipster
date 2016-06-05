(function() {
    'use strict';

    angular
        .module('kcotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rezervacija-prodaja', {
            parent: 'entity',
            url: '/rezervacija-prodaja?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RezervacijaProdajas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rezervacija-prodaja/rezervacija-prodajas.html',
                    controller: 'RezervacijaProdajaController',
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
        .state('rezervacija-prodaja-detail', {
            parent: 'entity',
            url: '/rezervacija-prodaja/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RezervacijaProdaja'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rezervacija-prodaja/rezervacija-prodaja-detail.html',
                    controller: 'RezervacijaProdajaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RezervacijaProdaja', function($stateParams, RezervacijaProdaja) {
                    return RezervacijaProdaja.get({id : $stateParams.id});
                }]
            }
        })
        .state('rezervacija-prodaja.new', {
            parent: 'rezervacija-prodaja',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rezervacija-prodaja/rezervacija-prodaja-dialog.html',
                    controller: 'RezervacijaProdajaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                rezervisao_ime: null,
                                cena: null,
                                broj_karata: null,
                                br_male_dece: null,
                                br_velike_dece: null,
                                firma: null,
                                aktivna_rez: null,
                                opis: null,
                                status_rez_prod: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rezervacija-prodaja', null, { reload: true });
                }, function() {
                    $state.go('rezervacija-prodaja');
                });
            }]
        })
        .state('rezervacija-prodaja.edit', {
            parent: 'rezervacija-prodaja',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rezervacija-prodaja/rezervacija-prodaja-dialog.html',
                    controller: 'RezervacijaProdajaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RezervacijaProdaja', function(RezervacijaProdaja) {
                            return RezervacijaProdaja.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('rezervacija-prodaja', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rezervacija-prodaja.delete', {
            parent: 'rezervacija-prodaja',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rezervacija-prodaja/rezervacija-prodaja-delete-dialog.html',
                    controller: 'RezervacijaProdajaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RezervacijaProdaja', function(RezervacijaProdaja) {
                            return RezervacijaProdaja.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('rezervacija-prodaja', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
