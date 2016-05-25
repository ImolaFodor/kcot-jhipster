(function() {
    'use strict';
    angular
        .module('kcotApp')
        .factory('Skola', Skola);

    Skola.$inject = ['$resource'];

    function Skola ($resource) {
        var resourceUrl =  'api/skolas/:id';

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
