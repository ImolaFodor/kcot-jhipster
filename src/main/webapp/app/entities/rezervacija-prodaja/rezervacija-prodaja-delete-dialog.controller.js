(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('RezervacijaProdajaDeleteController',RezervacijaProdajaDeleteController);

    RezervacijaProdajaDeleteController.$inject = ['$uibModalInstance', 'entity', 'RezervacijaProdaja'];

    function RezervacijaProdajaDeleteController($uibModalInstance, entity, RezervacijaProdaja) {
        var vm = this;
        vm.rezervacijaProdaja = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            RezervacijaProdaja.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
