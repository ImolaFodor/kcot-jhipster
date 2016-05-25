'use strict';

describe('Controller Tests', function() {

    describe('UGaleriji Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockUGaleriji, MockIzlozena_dela, MockDonirana_dela, MockPozvani;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockUGaleriji = jasmine.createSpy('MockUGaleriji');
            MockIzlozena_dela = jasmine.createSpy('MockIzlozena_dela');
            MockDonirana_dela = jasmine.createSpy('MockDonirana_dela');
            MockPozvani = jasmine.createSpy('MockPozvani');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'UGaleriji': MockUGaleriji,
                'Izlozena_dela': MockIzlozena_dela,
                'Donirana_dela': MockDonirana_dela,
                'Pozvani': MockPozvani
            };
            createController = function() {
                $injector.get('$controller')("UGalerijiDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kcotApp:uGalerijiUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
