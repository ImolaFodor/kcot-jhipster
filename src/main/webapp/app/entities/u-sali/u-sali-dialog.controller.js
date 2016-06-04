(function() {
    'use strict';

    angular
        .module('kcotApp')
        .controller('USaliDialogController', USaliDialogController);

    USaliDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'USali'];

    function USaliDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, USali) {
        var vm = this;
        vm.uSali = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('kcotApp:uSaliUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.uSali.id !== null) {
                USali.update(vm.uSali, onSaveSuccess, onSaveError);
            } else {
                USali.save(vm.uSali, onSaveSuccess, onSaveError);
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
