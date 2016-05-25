(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('Donirana_delaDetailController', Donirana_delaDetailController);

    Donirana_delaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Donirana_dela', 'UmetnickoDelo', 'UGaleriji'];

    function Donirana_delaDetailController($scope, $rootScope, $stateParams, entity, Donirana_dela, UmetnickoDelo, UGaleriji) {
        var vm = this;
        vm.donirana_dela = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:donirana_delaUpdate', function(event, result) {
            vm.donirana_dela = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
