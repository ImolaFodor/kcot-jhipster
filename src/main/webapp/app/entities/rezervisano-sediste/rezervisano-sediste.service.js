(function() {
    'use strict';
    angular
        .module('kcotApp')
        .factory('RezervisanoSediste', RezervisanoSediste);

    RezervisanoSediste.$inject = ['$resource'];

    function RezervisanoSediste ($resource) {
        var resourceUrl =  'api/rezervisano-sedistes/:id';

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
