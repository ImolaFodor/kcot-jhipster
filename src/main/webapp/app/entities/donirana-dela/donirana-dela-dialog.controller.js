(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('Donirana_delaDialogController', Donirana_delaDialogController);

    Donirana_delaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Donirana_dela', 'UmetnickoDelo', 'UGaleriji'];

    function Donirana_delaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Donirana_dela, UmetnickoDelo, UGaleriji) {
        var vm = this;
        vm.donirana_dela = entity;
        vm.umetnickodelos = UmetnickoDelo.query();
        vm.ugalerijis = UGaleriji.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kcotApp:donirana_delaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.donirana_dela.id !== null) {
                Donirana_dela.update(vm.donirana_dela, onSaveSuccess, onSaveError);
            } else {
                Donirana_dela.save(vm.donirana_dela, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
