(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('GostDeleteController',GostDeleteController);

    GostDeleteController.$inject = ['$uibModalInstance', 'entity', 'Gost'];

    function GostDeleteController($uibModalInstance, entity, Gost) {
        var vm = this;
        vm.gost = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Gost.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
