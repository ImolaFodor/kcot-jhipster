(function() {
    'use strict';
    angular
        .module('kcotApp')
        .factory('DnevniPazar', DnevniPazar);

    DnevniPazar.$inject = ['$resource'];

    function DnevniPazar ($resource) {
        var resourceUrl =  'api/dnevni-pazars/:id';

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
