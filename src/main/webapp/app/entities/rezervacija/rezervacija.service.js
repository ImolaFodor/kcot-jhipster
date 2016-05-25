(function() {
    'use strict';
    angular
        .module('kcotApp')
        .factory('Rezervacija', Rezervacija);

    Rezervacija.$inject = ['$resource'];

    function Rezervacija ($resource) {
        var resourceUrl =  'api/rezervacijas/:id';

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
