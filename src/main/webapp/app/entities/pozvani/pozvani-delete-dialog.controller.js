(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('PozvaniDeleteController',PozvaniDeleteController);

    PozvaniDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pozvani'];

    function PozvaniDeleteController($uibModalInstance, entity, Pozvani) {
        var vm = this;
        vm.pozvani = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Pozvani.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
