(function() {
    'use strict';
    angular
        .module('kcotApp')
        .factory('Pozvani', Pozvani);

    Pozvani.$inject = ['$resource'];

    function Pozvani ($resource) {
        var resourceUrl =  'api/pozvanis/:id';

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
