(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('PozvaniDetailController', PozvaniDetailController);

    PozvaniDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Pozvani', 'UGaleriji', 'Gost'];

    function PozvaniDetailController($scope, $rootScope, $stateParams, entity, Pozvani, UGaleriji, Gost) {
        var vm = this;
        vm.pozvani = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:pozvaniUpdate', function(event, result) {
            vm.pozvani = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
