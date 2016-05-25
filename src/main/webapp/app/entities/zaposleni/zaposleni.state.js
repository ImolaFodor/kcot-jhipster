(function() {
    'use strict';

    angular
        .module('kcotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('zaposleni', {
            parent: 'entity',
            url: '/zaposleni?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Zaposlenis'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/zaposleni/zaposlenis.html',
                    controller: 'ZaposleniController',
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
        .state('zaposleni-detail', {
            parent: 'entity',
            url: '/zaposleni/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Zaposleni'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/zaposleni/zaposleni-detail.html',
                    controller: 'ZaposleniDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Zaposleni', function($stateParams, Zaposleni) {
                    return Zaposleni.get({id : $stateParams.id});
                }]
            }
        })
        .state('zaposleni.new', {
            parent: 'zaposleni',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zaposleni/zaposleni-dialog.html',
                    controller: 'ZaposleniDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ime: null,
                                prz: null,
                                korisnicko_ime: null,
                                lozinka: null,
                                tip: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('zaposleni', null, { reload: true });
                }, function() {
                    $state.go('zaposleni');
                });
            }]
        })
        .state('zaposleni.edit', {
            parent: 'zaposleni',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zaposleni/zaposleni-dialog.html',
                    controller: 'ZaposleniDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Zaposleni', function(Zaposleni) {
                            return Zaposleni.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('zaposleni', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('zaposleni.delete', {
            parent: 'zaposleni',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zaposleni/zaposleni-delete-dialog.html',
                    controller: 'ZaposleniDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Zaposleni', function(Zaposleni) {
                            return Zaposleni.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('zaposleni', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
