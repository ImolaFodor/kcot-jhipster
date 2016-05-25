(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('Izlozena_delaDeleteController',Izlozena_delaDeleteController);

    Izlozena_delaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Izlozena_dela'];

    function Izlozena_delaDeleteController($uibModalInstance, entity, Izlozena_dela) {
        var vm = this;
        vm.izlozena_dela = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Izlozena_dela.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
