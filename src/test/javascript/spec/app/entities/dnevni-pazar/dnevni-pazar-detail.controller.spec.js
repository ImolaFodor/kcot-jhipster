'use strict';

describe('Controller Tests', function() {

    describe('DnevniPazar Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDnevniPazar;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDnevniPazar = jasmine.createSpy('MockDnevniPazar');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DnevniPazar': MockDnevniPazar
            };
            createController = function() {
                $injector.get('$controller')("DnevniPazarDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kcotApp:dnevniPazarUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
