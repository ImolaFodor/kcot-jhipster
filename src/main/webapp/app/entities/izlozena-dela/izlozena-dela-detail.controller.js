(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('Izlozena_delaDetailController', Izlozena_delaDetailController);

    Izlozena_delaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Izlozena_dela', 'UmetnickoDelo', 'UGaleriji'];

    function Izlozena_delaDetailController($scope, $rootScope, $stateParams, entity, Izlozena_dela, UmetnickoDelo, UGaleriji) {
        var vm = this;
        vm.izlozena_dela = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:izlozena_delaUpdate', function(event, result) {
            vm.izlozena_dela = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
