'use strict';

describe('Controller Tests', function() {

    describe('Donirana_dela Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDonirana_dela, MockUmetnickoDelo, MockUGaleriji;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDonirana_dela = jasmine.createSpy('MockDonirana_dela');
            MockUmetnickoDelo = jasmine.createSpy('MockUmetnickoDelo');
            MockUGaleriji = jasmine.createSpy('MockUGaleriji');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Donirana_dela': MockDonirana_dela,
                'UmetnickoDelo': MockUmetnickoDelo,
                'UGaleriji': MockUGaleriji
            };
            createController = function() {
                $injector.get('$controller')("Donirana_delaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kcotApp:donirana_delaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
