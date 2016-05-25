(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('UmetnickoDeloDeleteController',UmetnickoDeloDeleteController);

    UmetnickoDeloDeleteController.$inject = ['$uibModalInstance', 'entity', 'UmetnickoDelo'];

    function UmetnickoDeloDeleteController($uibModalInstance, entity, UmetnickoDelo) {
        var vm = this;
        vm.umetnickoDelo = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            UmetnickoDelo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
