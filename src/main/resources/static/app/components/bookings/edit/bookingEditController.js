angular.module('app')
    .controller('BookingEditController', function ($rootScope, $window, $routeParams, $location, $timeout, BookingService, Booking, AccommodationService) {
        $rootScope.authUser = JSON.parse($window.sessionStorage.getItem('authUser'));
        $rootScope.authenticated = JSON.parse($window.sessionStorage.getItem('authenticated'));

        if (!$rootScope.authenticated)
            $location.path('/login');

        const vm = this;
        const bookingId = $routeParams.bookingId;
        const accommodationId = $routeParams.accommodationId;

        if (accommodationId) {
            const accommodation = AccommodationService.get(accommodationId);
            if(accommodation) {
                if (bookingId) {
                    vm.booking = BookingService.get(bookingId);
                    vm.editAllowed = $rootScope.authUser !== null && vm.booking.user.id == $rootScope.authUser.id;
                    vm.owner = accommodationId == vm.booking.accommodation.id;
                } else {
                    vm.booking = new Booking();
                    vm.booking.user = $rootScope.authUser;
                    vm.booking.accommodation = accommodation;
                    vm.booking.status = "SUBMITTED";
                    vm.editAllowed = $rootScope.authenticated;
                }
            } else {
                $location.path('/error');
            }
        }

        const saveCallback = () => {
            $location.path(`/${vm.booking.accommodation.id}/bookings/${vm.booking.id}`);
        };
        const errorCallback = err => {
            vm.msg = `Błąd zapisu: ${err.data.message}`;
        };

        vm.saveBooking = () => {
            BookingService.save(vm.booking)
                .then(saveCallback)
                .catch(errorCallback);
        };

        const updateCallback = response => vm.msg = 'SUCCESS!';
        vm.updateBooking = () => {
            BookingService.update(vm.booking)
                .then(updateCallback)
                .catch(errorCallback);
        };

        vm.bookingStatuses = ["VERIFIED", "SUBMITTED", "REJECTED"];
    });