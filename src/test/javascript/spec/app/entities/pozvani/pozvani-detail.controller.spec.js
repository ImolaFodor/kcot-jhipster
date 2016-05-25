'use strict';

describe('Controller Tests', function() {

    describe('Pozvani Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPozvani, MockUGaleriji, MockGost;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPozvani = jasmine.createSpy('MockPozvani');
            MockUGaleriji = jasmine.createSpy('MockUGaleriji');
            MockGost = jasmine.createSpy('MockGost');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Pozvani': MockPozvani,
                'UGaleriji': MockUGaleriji,
                'Gost': MockGost
            };
            createController = function() {
                $injector.get('$controller')("PozvaniDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kcotApp:pozvaniUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
