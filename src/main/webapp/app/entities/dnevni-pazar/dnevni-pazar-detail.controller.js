(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('DnevniPazarDetailController', DnevniPazarDetailController);

    DnevniPazarDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'DnevniPazar'];

    function DnevniPazarDetailController($scope, $rootScope, $stateParams, entity, DnevniPazar) {
        var vm = this;
        vm.dnevniPazar = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:dnevniPazarUpdate', function(event, result) {
            vm.dnevniPazar = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
