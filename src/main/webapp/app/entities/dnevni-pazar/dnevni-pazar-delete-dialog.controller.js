(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('DnevniPazarDeleteController',DnevniPazarDeleteController);

    DnevniPazarDeleteController.$inject = ['$uibModalInstance', 'entity', 'DnevniPazar'];

    function DnevniPazarDeleteController($uibModalInstance, entity, DnevniPazar) {
        var vm = this;
        vm.dnevniPazar = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            DnevniPazar.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
