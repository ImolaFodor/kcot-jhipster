(function() {
    'use strict';
    angular
        .module('kcotApp')
        .factory('Zaposleni', Zaposleni);

    Zaposleni.$inject = ['$resource'];

    function Zaposleni ($resource) {
        var resourceUrl =  'api/zaposlenis/:id';

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
