(function() {
    'use strict';
    angular
        .module('kcotApp')
        .factory('Donirana_dela', Donirana_dela);

    Donirana_dela.$inject = ['$resource'];

    function Donirana_dela ($resource) {
        var resourceUrl =  'api/donirana-delas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
