(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('PozvaniDialogController', PozvaniDialogController);

    PozvaniDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pozvani', 'UGaleriji', 'Gost'];

    function PozvaniDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pozvani, UGaleriji, Gost) {
        var vm = this;
        vm.pozvani = entity;
        vm.ugalerijis = UGaleriji.query();
        vm.gosts = Gost.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kcotApp:pozvaniUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.pozvani.id !== null) {
                Pozvani.update(vm.pozvani, onSaveSuccess, onSaveError);
            } else {
                Pozvani.save(vm.pozvani, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
