(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('GostDialogController', GostDialogController);

    GostDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Gost', 'Pozvani', 'RezervacijaProdaja'];

    function GostDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Gost, Pozvani, RezervacijaProdaja) {
        var vm = this;
        vm.gost = entity;
        vm.pozvanis = Pozvani.query();
        vm.rezervacijaprodajas = RezervacijaProdaja.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kcotApp:gostUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.gost.id !== null) {
                Gost.update(vm.gost, onSaveSuccess, onSaveError);
            } else {
                Gost.save(vm.gost, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
