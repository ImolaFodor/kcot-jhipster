'use strict';

describe('Controller Tests', function() {

    describe('Gost Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockGost, MockPozvani, MockRezervacijaProdaja;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockGost = jasmine.createSpy('MockGost');
            MockPozvani = jasmine.createSpy('MockPozvani');
            MockRezervacijaProdaja = jasmine.createSpy('MockRezervacijaProdaja');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Gost': MockGost,
                'Pozvani': MockPozvani,
                'RezervacijaProdaja': MockRezervacijaProdaja
            };
            createController = function() {
                $injector.get('$controller')("GostDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kcotApp:gostUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
