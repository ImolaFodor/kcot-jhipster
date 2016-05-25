(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('SkolaDeleteController',SkolaDeleteController);

    SkolaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Skola'];

    function SkolaDeleteController($uibModalInstance, entity, Skola) {
        var vm = this;
        vm.skola = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Skola.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
