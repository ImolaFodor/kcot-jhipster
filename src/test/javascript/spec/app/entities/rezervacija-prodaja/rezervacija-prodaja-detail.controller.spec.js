'use strict';

describe('Controller Tests', function() {

    describe('RezervacijaProdaja Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRezervacijaProdaja, MockGost, MockSkola, MockZaposleni, MockUSali, MockRezervisanoSediste;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRezervacijaProdaja = jasmine.createSpy('MockRezervacijaProdaja');
            MockGost = jasmine.createSpy('MockGost');
            MockSkola = jasmine.createSpy('MockSkola');
            MockZaposleni = jasmine.createSpy('MockZaposleni');
            MockUSali = jasmine.createSpy('MockUSali');
            MockRezervisanoSediste = jasmine.createSpy('MockRezervisanoSediste');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RezervacijaProdaja': MockRezervacijaProdaja,
                'Gost': MockGost,
                'Skola': MockSkola,
                'Zaposleni': MockZaposleni,
                'USali': MockUSali,
                'RezervisanoSediste': MockRezervisanoSediste
            };
            createController = function() {
                $injector.get('$controller')("RezervacijaProdajaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kcotApp:rezervacijaProdajaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
