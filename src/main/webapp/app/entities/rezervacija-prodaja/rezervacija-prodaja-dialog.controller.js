(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('RezervacijaProdajaDialogController', RezervacijaProdajaDialogController);

    RezervacijaProdajaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RezervacijaProdaja', 'Gost', 'Skola', 'Zaposleni', 'USali', 'RezervisanoSediste'];

    function RezervacijaProdajaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RezervacijaProdaja, Gost, Skola, Zaposleni, USali, RezervisanoSediste) {
        var vm = this;
        vm.rezervacijaProdaja = entity;
        vm.gosts = Gost.query();
        vm.skolas = Skola.query();
        vm.zaposlenis = Zaposleni.query();
        vm.usalis = USali.query();
        vm.rezervisanosedistes = RezervisanoSediste.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kcotApp:rezervacijaProdajaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.rezervacijaProdaja.id !== null) {
                RezervacijaProdaja.update(vm.rezervacijaProdaja, onSaveSuccess, onSaveError);
            } else {
                RezervacijaProdaja.save(vm.rezervacijaProdaja, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
