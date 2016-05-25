(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('ZaposleniDialogController', ZaposleniDialogController);

    ZaposleniDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Zaposleni', 'RezervacijaProdaja'];

    function ZaposleniDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Zaposleni, RezervacijaProdaja) {
        var vm = this;
        vm.zaposleni = entity;
        vm.rezervacijaprodajas = RezervacijaProdaja.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kcotApp:zaposleniUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.zaposleni.id !== null) {
                Zaposleni.update(vm.zaposleni, onSaveSuccess, onSaveError);
            } else {
                Zaposleni.save(vm.zaposleni, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
