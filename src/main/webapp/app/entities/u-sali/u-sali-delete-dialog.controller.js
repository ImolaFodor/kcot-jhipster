(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('USaliDeleteController',USaliDeleteController);

    USaliDeleteController.$inject = ['$uibModalInstance', 'entity', 'USali'];

    function USaliDeleteController($uibModalInstance, entity, USali) {
        var vm = this;
        vm.uSali = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            USali.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
