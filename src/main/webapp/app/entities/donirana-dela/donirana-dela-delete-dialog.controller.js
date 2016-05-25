(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('Donirana_delaDeleteController',Donirana_delaDeleteController);

    Donirana_delaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Donirana_dela'];

    function Donirana_delaDeleteController($uibModalInstance, entity, Donirana_dela) {
        var vm = this;
        vm.donirana_dela = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Donirana_dela.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
