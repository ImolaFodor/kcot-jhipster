(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('RezervisanoSedisteDeleteController',RezervisanoSedisteDeleteController);

    RezervisanoSedisteDeleteController.$inject = ['$uibModalInstance', 'entity', 'RezervisanoSediste'];

    function RezervisanoSedisteDeleteController($uibModalInstance, entity, RezervisanoSediste) {
        var vm = this;
        vm.rezervisanoSediste = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            RezervisanoSediste.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
