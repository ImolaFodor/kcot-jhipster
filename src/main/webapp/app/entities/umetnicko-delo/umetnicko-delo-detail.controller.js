(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('UmetnickoDeloDetailController', UmetnickoDeloDetailController);

    UmetnickoDeloDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'UmetnickoDelo', 'Donirana_dela', 'Izlozena_dela'];

    function UmetnickoDeloDetailController($scope, $rootScope, $stateParams, entity, UmetnickoDelo, Donirana_dela, Izlozena_dela) {
        var vm = this;
        vm.umetnickoDelo = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:umetnickoDeloUpdate', function(event, result) {
            vm.umetnickoDelo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
