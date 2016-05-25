(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('SkolaDialogController', SkolaDialogController);

    SkolaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Skola'];

    function SkolaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Skola) {
        var vm = this;
        vm.skola = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kcotApp:skolaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.skola.id !== null) {
                Skola.update(vm.skola, onSaveSuccess, onSaveError);
            } else {
                Skola.save(vm.skola, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
