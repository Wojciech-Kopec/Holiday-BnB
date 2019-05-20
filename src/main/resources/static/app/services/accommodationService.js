angular.module('app')
    .constant('ACCOMMODATIONS_ENDPOINT', '/api/accommodations/:id')
    .constant('ACCOMMODATIONS_BOOKINGS_ENDPOINT', '/api/accommodations/:id/bookings')
    .factory('Accommodation', function ($resource, ACCOMMODATIONS_ENDPOINT, ACCOMMODATIONS_BOOKINGS_ENDPOINT) {
        return $resource(ACCOMMODATIONS_ENDPOINT, {id: '@_id'}, {
            update: {
                method: 'PUT'
            },
            getBookings: {
                method: 'GET',
                url: ACCOMMODATIONS_BOOKINGS_ENDPOINT,
                params: {id: '@id'},
                isArray: true
            }
        });
    })
    .service('AccommodationService', function (Accommodation) {
        this.getAll = params => Accommodation.query(params);
        this.get = index => Accommodation.get({id: index});
        this.getBookings = index => Accommodation.getBookings({id: index});
        this.save = accommodation => accommodation.$save();
        this.update = accommodation => accommodation.$update({id: accommodation.id});
        this.remove = accommodation => accommodation.$remove({id: accommodation.id});
        this.getAmenities = () => {
            var list = [];
            console.log('Here We Go!');
            // const promise = this.getAll();
            var accommodations = this.getAll();
            // promise.then(function (accommodations) {
            console.log(accommodations);
            accommodations.forEach(function (accommodation) {
                var amenities = accommodation.amenities;
                console.log(amenities);
                amenities.forEach(function (amenity) {
                    console.log(amenity.type);
                    list.push(amenity.type);
                })
            })
            /*})*/;
            console.log('The End');
            return list;
        }
    });