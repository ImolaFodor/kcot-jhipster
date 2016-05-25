(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('RezervisanoSedisteDialogController', RezervisanoSedisteDialogController);

    RezervisanoSedisteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RezervisanoSediste', 'Sediste', 'RezervacijaProdaja'];

    function RezervisanoSedisteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RezervisanoSediste, Sediste, RezervacijaProdaja) {
        var vm = this;
        vm.rezervisanoSediste = entity;
        vm.sedistes = Sediste.query();
        vm.rezervacijaprodajas = RezervacijaProdaja.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kcotApp:rezervisanoSedisteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.rezervisanoSediste.id !== null) {
                RezervisanoSediste.update(vm.rezervisanoSediste, onSaveSuccess, onSaveError);
            } else {
                RezervisanoSediste.save(vm.rezervisanoSediste, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
