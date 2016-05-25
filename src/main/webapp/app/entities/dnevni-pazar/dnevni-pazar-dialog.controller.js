(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('DnevniPazarDialogController', DnevniPazarDialogController);

    DnevniPazarDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DnevniPazar'];

    function DnevniPazarDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DnevniPazar) {
        var vm = this;
        vm.dnevniPazar = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kcotApp:dnevniPazarUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.dnevniPazar.id !== null) {
                DnevniPazar.update(vm.dnevniPazar, onSaveSuccess, onSaveError);
            } else {
                DnevniPazar.save(vm.dnevniPazar, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
