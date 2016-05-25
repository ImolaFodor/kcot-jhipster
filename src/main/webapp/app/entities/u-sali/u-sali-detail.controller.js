(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('USaliDetailController', USaliDetailController);

    USaliDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'USali', 'RezervacijaProdaja'];

    function USaliDetailController($scope, $rootScope, $stateParams, entity, USali, RezervacijaProdaja) {
        var vm = this;
        vm.uSali = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:uSaliUpdate', function(event, result) {
            vm.uSali = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
