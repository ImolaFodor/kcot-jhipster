(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('ZaposleniDeleteController',ZaposleniDeleteController);

    ZaposleniDeleteController.$inject = ['$uibModalInstance', 'entity', 'Zaposleni'];

    function ZaposleniDeleteController($uibModalInstance, entity, Zaposleni) {
        var vm = this;
        vm.zaposleni = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Zaposleni.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
