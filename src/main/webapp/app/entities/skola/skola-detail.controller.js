(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('SkolaDetailController', SkolaDetailController);

    SkolaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Skola'];

    function SkolaDetailController($scope, $rootScope, $stateParams, entity, Skola) {
        var vm = this;
        vm.skola = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:skolaUpdate', function(event, result) {
            vm.skola = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
