angular.module('app')
    .controller('BookingEditController', function ($routeParams, $location, $timeout, BookingService, Booking) {
        const vm = this;
        const bookingId = $routeParams.bookingId;
        if (bookingId)
            vm.booking = BookingService.get(bookingId);
        else
            vm.booking = new Booking();

        const saveCallback = () => {
            $location.path(`/booking-edit/${vm.booking.id}`);
        };
        const errorCallback = err => {
            vm.msg = `Błąd zapisu: ${err.data.message}`;
        };

        vm.saveBooking = () => {
            BookingService.save(vm.booking)
                .then(saveCallback)
                .catch(errorCallback);
        };

        const updateCallback = response => vm.msg = 'Zapisano zmiany';
        vm.updateBooking = () => {
            BookingService.update(vm.booking)
                .then(updateCallback)
                .catch(errorCallback);
        };

    });