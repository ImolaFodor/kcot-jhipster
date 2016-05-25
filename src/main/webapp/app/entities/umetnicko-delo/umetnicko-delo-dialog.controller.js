(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('UmetnickoDeloDialogController', UmetnickoDeloDialogController);

    UmetnickoDeloDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UmetnickoDelo', 'Donirana_dela', 'Izlozena_dela'];

    function UmetnickoDeloDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UmetnickoDelo, Donirana_dela, Izlozena_dela) {
        var vm = this;
        vm.umetnickoDelo = entity;
        vm.donirana_delas = Donirana_dela.query();
        vm.izlozena_delas = Izlozena_dela.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kcotApp:umetnickoDeloUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.umetnickoDelo.id !== null) {
                UmetnickoDelo.update(vm.umetnickoDelo, onSaveSuccess, onSaveError);
            } else {
                UmetnickoDelo.save(vm.umetnickoDelo, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
