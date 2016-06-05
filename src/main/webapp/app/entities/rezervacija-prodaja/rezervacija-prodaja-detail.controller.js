(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('RezervacijaProdajaDetailController', RezervacijaProdajaDetailController);

    RezervacijaProdajaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'RezervacijaProdaja', 'Gost', 'Skola', 'Zaposleni', 'USali', 'RezervisanoSediste'];

    function RezervacijaProdajaDetailController($scope, $rootScope, $stateParams, entity, RezervacijaProdaja, Gost, Skola, Zaposleni, USali, RezervisanoSediste) {
        var vm = this;
        vm.rezervacijaProdaja = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:rezervacijaProdajaUpdate', function(event, result) {
            vm.rezervacijaProdaja = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
