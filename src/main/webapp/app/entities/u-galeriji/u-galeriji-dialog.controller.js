(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('UGalerijiDialogController', UGalerijiDialogController);

    UGalerijiDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UGaleriji', 'Izlozena_dela', 'Donirana_dela', 'Pozvani'];

    function UGalerijiDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UGaleriji, Izlozena_dela, Donirana_dela, Pozvani) {
        var vm = this;
        vm.uGaleriji = entity;
        vm.izlozena_delas = Izlozena_dela.query();
        vm.donirana_delas = Donirana_dela.query();
        vm.pozvanis = Pozvani.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kcotApp:uGalerijiUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.uGaleriji.id !== null) {
                UGaleriji.update(vm.uGaleriji, onSaveSuccess, onSaveError);
            } else {
                UGaleriji.save(vm.uGaleriji, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.datum = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
