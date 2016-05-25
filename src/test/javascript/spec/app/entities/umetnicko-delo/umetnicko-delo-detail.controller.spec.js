'use strict';

describe('Controller Tests', function() {

    describe('UmetnickoDelo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockUmetnickoDelo, MockDonirana_dela, MockIzlozena_dela;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockUmetnickoDelo = jasmine.createSpy('MockUmetnickoDelo');
            MockDonirana_dela = jasmine.createSpy('MockDonirana_dela');
            MockIzlozena_dela = jasmine.createSpy('MockIzlozena_dela');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'UmetnickoDelo': MockUmetnickoDelo,
                'Donirana_dela': MockDonirana_dela,
                'Izlozena_dela': MockIzlozena_dela
            };
            createController = function() {
                $injector.get('$controller')("UmetnickoDeloDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kcotApp:umetnickoDeloUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
