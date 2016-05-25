'use strict';

describe('Controller Tests', function() {

    describe('RezervisanoSediste Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRezervisanoSediste, MockSediste, MockRezervacijaProdaja;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRezervisanoSediste = jasmine.createSpy('MockRezervisanoSediste');
            MockSediste = jasmine.createSpy('MockSediste');
            MockRezervacijaProdaja = jasmine.createSpy('MockRezervacijaProdaja');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RezervisanoSediste': MockRezervisanoSediste,
                'Sediste': MockSediste,
                'RezervacijaProdaja': MockRezervacijaProdaja
            };
            createController = function() {
                $injector.get('$controller')("RezervisanoSedisteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kcotApp:rezervisanoSedisteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
