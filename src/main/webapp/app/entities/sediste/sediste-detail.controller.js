(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('SedisteDetailController', SedisteDetailController);

    SedisteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Sediste', 'RezervisanoSediste'];

    function SedisteDetailController($scope, $rootScope, $stateParams, entity, Sediste, RezervisanoSediste) {
        var vm = this;
        vm.sediste = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:sedisteUpdate', function(event, result) {
            vm.sediste = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
