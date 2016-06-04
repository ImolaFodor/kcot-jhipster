(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('UGalerijiDetailController', UGalerijiDetailController);

    UGalerijiDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'UGaleriji'];

    function UGalerijiDetailController($scope, $rootScope, $stateParams, entity, UGaleriji) {
        var vm = this;
        vm.uGaleriji = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:uGalerijiUpdate', function(event, result) {
            vm.uGaleriji = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
