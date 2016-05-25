(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('RezervacijaDeleteController',RezervacijaDeleteController);

    RezervacijaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Rezervacija'];

    function RezervacijaDeleteController($uibModalInstance, entity, Rezervacija) {
        var vm = this;
        vm.rezervacija = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Rezervacija.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
