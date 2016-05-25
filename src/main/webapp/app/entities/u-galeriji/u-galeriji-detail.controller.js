(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('UGalerijiDetailController', UGalerijiDetailController);

    UGalerijiDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'UGaleriji', 'Izlozena_dela', 'Donirana_dela', 'Pozvani'];

    function UGalerijiDetailController($scope, $rootScope, $stateParams, entity, UGaleriji, Izlozena_dela, Donirana_dela, Pozvani) {
        var vm = this;
        vm.uGaleriji = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:uGalerijiUpdate', function(event, result) {
            vm.uGaleriji = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
