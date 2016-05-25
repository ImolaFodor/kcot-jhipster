'use strict';

describe('Controller Tests', function() {

    describe('Zaposleni Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockZaposleni, MockRezervacijaProdaja;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockZaposleni = jasmine.createSpy('MockZaposleni');
            MockRezervacijaProdaja = jasmine.createSpy('MockRezervacijaProdaja');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Zaposleni': MockZaposleni,
                'RezervacijaProdaja': MockRezervacijaProdaja
            };
            createController = function() {
                $injector.get('$controller')("ZaposleniDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kcotApp:zaposleniUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
