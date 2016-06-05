(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('SedisteDialogController', SedisteDialogController);

    SedisteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sediste', 'RezervisanoSediste'];

    function SedisteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sediste, RezervisanoSediste) {
        var vm = this;
        vm.sediste = entity;
        vm.rezervisanosedistes = RezervisanoSediste.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kcotApp:sedisteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.sediste.id !== null) {
                Sediste.update(vm.sediste, onSaveSuccess, onSaveError);
            } else {
                Sediste.save(vm.sediste, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
