(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('RezervisanoSedisteDetailController', RezervisanoSedisteDetailController);

    RezervisanoSedisteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'RezervisanoSediste', 'Sediste', 'RezervacijaProdaja'];

    function RezervisanoSedisteDetailController($scope, $rootScope, $stateParams, entity, RezervisanoSediste, Sediste, RezervacijaProdaja) {
        var vm = this;
        vm.rezervisanoSediste = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:rezervisanoSedisteUpdate', function(event, result) {
            vm.rezervisanoSediste = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
