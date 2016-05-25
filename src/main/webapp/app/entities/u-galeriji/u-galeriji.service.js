(function() {
    'use strict';
    angular
        .module('kcotApp')
        .factory('UGaleriji', UGaleriji);

    UGaleriji.$inject = ['$resource', 'DateUtils'];

    function UGaleriji ($resource, DateUtils) {
        var resourceUrl =  'api/u-galerijis/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.datum = DateUtils.convertLocalDateFromServer(data.datum);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.datum = DateUtils.convertLocalDateToServer(data.datum);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.datum = DateUtils.convertLocalDateToServer(data.datum);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
