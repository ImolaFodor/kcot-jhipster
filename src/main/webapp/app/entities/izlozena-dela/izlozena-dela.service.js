(function() {
    'use strict';
    angular
        .module('kcotApp')
        .factory('Izlozena_dela', Izlozena_dela);

    Izlozena_dela.$inject = ['$resource'];

    function Izlozena_dela ($resource) {
        var resourceUrl =  'api/izlozena-delas/:id';

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
