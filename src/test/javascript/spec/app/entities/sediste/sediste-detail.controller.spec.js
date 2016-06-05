'use strict';

describe('Controller Tests', function() {

    describe('Sediste Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSediste, MockRezervisanoSediste;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSediste = jasmine.createSpy('MockSediste');
            MockRezervisanoSediste = jasmine.createSpy('MockRezervisanoSediste');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Sediste': MockSediste,
                'RezervisanoSediste': MockRezervisanoSediste
            };
            createController = function() {
                $injector.get('$controller')("SedisteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kcotApp:sedisteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
