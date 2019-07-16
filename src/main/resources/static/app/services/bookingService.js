angular.module('app')
    .constant('BOOKING_ENDPOINT', API_ROOT + '/api/bookings/:id')
    .factory('Booking', function($resource, BOOKING_ENDPOINT) {
        return $resource(BOOKING_ENDPOINT, { id: '@_id' }, {
            update: {
                method: 'PUT'
            }
        });
    })
    .service('BookingService', function(Booking) {
        this.getAll = () => Booking.query();
        this.get = index => Booking.get({id: index});
        this.save = booking => booking.$save();
        this.update = booking => booking.$update({id: booking.id});
        this.remove = booking => booking.$remove({id: booking.id});
    });