(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('ZaposleniDetailController', ZaposleniDetailController);

    ZaposleniDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Zaposleni', 'RezervacijaProdaja'];

    function ZaposleniDetailController($scope, $rootScope, $stateParams, entity, Zaposleni, RezervacijaProdaja) {
        var vm = this;
        vm.zaposleni = entity;
        
        var unsubscribe = $rootScope.$on('kcotApp:zaposleniUpdate', function(event, result) {
            vm.zaposleni = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
