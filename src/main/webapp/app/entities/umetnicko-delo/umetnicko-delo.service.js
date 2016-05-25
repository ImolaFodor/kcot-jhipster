(function() {
    'use strict';
    angular
        .module('kcotApp')
        .factory('UmetnickoDelo', UmetnickoDelo);

    UmetnickoDelo.$inject = ['$resource'];

    function UmetnickoDelo ($resource) {
        var resourceUrl =  'api/umetnicko-delos/:id';

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
