(function() {
    'use strict';
    angular
        .module('kcotApp')
        .factory('Sediste', Sediste);

    Sediste.$inject = ['$resource'];

    function Sediste ($resource) {
        var resourceUrl =  'api/sedistes/:id';

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
