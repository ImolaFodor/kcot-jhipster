(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('UGalerijiDeleteController',UGalerijiDeleteController);

    UGalerijiDeleteController.$inject = ['$uibModalInstance', 'entity', 'UGaleriji'];

    function UGalerijiDeleteController($uibModalInstance, entity, UGaleriji) {
        var vm = this;
        vm.uGaleriji = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            UGaleriji.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
