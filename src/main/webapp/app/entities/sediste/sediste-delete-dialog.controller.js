(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('SedisteDeleteController',SedisteDeleteController);

    SedisteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Sediste'];

    function SedisteDeleteController($uibModalInstance, entity, Sediste) {
        var vm = this;
        vm.sediste = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Sediste.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
