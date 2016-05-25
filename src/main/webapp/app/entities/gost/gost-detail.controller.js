(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('GostDetailController', GostDetailController);

    GostDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Gost', 'Pozvani', 'RezervacijaProdaja'];

    function GostDetailController($scope, $rootScope, $stateParams, entity, Gost, Pozvani, RezervacijaProdaja) {
        var vm = this;
        vm.gost = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:gostUpdate', function(event, result) {
            vm.gost = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
