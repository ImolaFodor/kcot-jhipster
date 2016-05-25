(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('RezervacijaDialogController', RezervacijaDialogController);

    RezervacijaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Rezervacija'];

    function RezervacijaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Rezervacija) {
        var vm = this;
        vm.rezervacija = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kcotApp:rezervacijaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.rezervacija.id !== null) {
                Rezervacija.update(vm.rezervacija, onSaveSuccess, onSaveError);
            } else {
                Rezervacija.save(vm.rezervacija, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
