'use strict';

describe('Controller Tests', function() {

    describe('UGaleriji Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockUGaleriji;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockUGaleriji = jasmine.createSpy('MockUGaleriji');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'UGaleriji': MockUGaleriji
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
