(function() {
    'use strict';
    angular
        .module('kcotApp')
        .factory('RezervacijaProdaja', RezervacijaProdaja);

    RezervacijaProdaja.$inject = ['$resource'];

    function RezervacijaProdaja ($resource) {
        var resourceUrl =  'api/rezervacija-prodajas/:id';

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
