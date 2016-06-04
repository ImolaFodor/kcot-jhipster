(function() {
    'use strict';

    angular
        .module('kcotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rezervisano-sediste', {
            parent: 'entity',
            url: '/rezervisano-sediste?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Rezervisana sedišta'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rezervisano-sediste/rezervisano-sedistes.html',
                    controller: 'RezervisanoSedisteController',
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
        .state('rezervisano-sediste-detail', {
            parent: 'entity',
            url: '/rezervisano-sediste/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RezervisanoSediste'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rezervisano-sediste/rezervisano-sediste-detail.html',
                    controller: 'RezervisanoSedisteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RezervisanoSediste', function($stateParams, RezervisanoSediste) {
                    return RezervisanoSediste.get({id : $stateParams.id});
                }]
            }
        })
        .state('rezervisano-sediste.new', {
            parent: 'rezervisano-sediste',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rezervisano-sediste/rezervisano-sediste-dialog.html',
                    controller: 'RezervisanoSedisteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                opis: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rezervisano-sediste', null, { reload: true });
                }, function() {
                    $state.go('rezervisano-sediste');
                });
            }]
        })
        .state('rezervisano-sediste.edit', {
            parent: 'rezervisano-sediste',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rezervisano-sediste/rezervisano-sediste-dialog.html',
                    controller: 'RezervisanoSedisteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RezervisanoSediste', function(RezervisanoSediste) {
                            return RezervisanoSediste.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('rezervisano-sediste', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rezervisano-sediste.delete', {
            parent: 'rezervisano-sediste',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rezervisano-sediste/rezervisano-sediste-delete-dialog.html',
                    controller: 'RezervisanoSedisteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RezervisanoSediste', function(RezervisanoSediste) {
                            return RezervisanoSediste.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('rezervisano-sediste', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
