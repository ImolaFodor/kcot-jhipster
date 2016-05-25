(function() {
    'use strict';
    angular
        .module('kcotApp')
        .factory('Gost', Gost);

    Gost.$inject = ['$resource'];

    function Gost ($resource) {
        var resourceUrl =  'api/gosts/:id';

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
