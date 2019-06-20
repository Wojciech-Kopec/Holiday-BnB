angular.module('app')
    .constant('ACCOMMODATIONS_ENDPOINT', '/api/accommodations/:id')
    .factory('Accommodation', function ($resource, $timeout, ACCOMMODATIONS_ENDPOINT) {
        return $resource(ACCOMMODATIONS_ENDPOINT, {id: '@_id'}, {
            update: {
                method: 'PUT'
            },
        });
    })
    .service('AccommodationService', function (Accommodation) {
        this.getAll = params => Accommodation.query(params);
        this.get = index => Accommodation.get({id: index});
        this.save = accommodation => accommodation.$save();
        this.update = accommodation => accommodation.$update({id: accommodation.id});
        this.remove = accommodation => accommodation.$remove({id: accommodation.id});
    });