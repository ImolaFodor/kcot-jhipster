(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('RezervacijaDetailController', RezervacijaDetailController);

    RezervacijaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Rezervacija'];

    function RezervacijaDetailController($scope, $rootScope, $stateParams, entity, Rezervacija) {
        var vm = this;
        vm.rezervacija = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:rezervacijaUpdate', function(event, result) {
            vm.rezervacija = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
