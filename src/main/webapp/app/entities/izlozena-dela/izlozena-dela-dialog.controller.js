(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('Izlozena_delaDialogController', Izlozena_delaDialogController);

    Izlozena_delaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Izlozena_dela', 'UmetnickoDelo', 'UGaleriji'];

    function Izlozena_delaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Izlozena_dela, UmetnickoDelo, UGaleriji) {
        var vm = this;
        vm.izlozena_dela = entity;
        vm.umetnickodelos = UmetnickoDelo.query();
        vm.ugalerijis = UGaleriji.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kcotApp:izlozena_delaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.izlozena_dela.id !== null) {
                Izlozena_dela.update(vm.izlozena_dela, onSaveSuccess, onSaveError);
            } else {
                Izlozena_dela.save(vm.izlozena_dela, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
