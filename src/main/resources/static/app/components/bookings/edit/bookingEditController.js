angular.module('app')
    .controller('BookingEditController', function ($rootScope, $window, $routeParams, $location, $timeout, BookingService, Booking, AccommodationService) {
            $rootScope.authUser = JSON.parse($window.sessionStorage.getItem('authUser'));
            $rootScope.authenticated = JSON.parse($window.sessionStorage.getItem('authenticated'));

            if ($rootScope.authenticated == null || $rootScope.authenticated === false) {
                console.log('User not authenticated, redirecting to /login');
                $location.path('/login');
            }
            const vm = this;
            const bookingId = $routeParams.bookingId;
            const accommodationId = $routeParams.accommodationId;
            var accommodation;

            if (accommodationId) {
                AccommodationService.get(accommodationId).$promise
                    .then(data => {
                        accommodation = data;

                        if (accommodation) {
                            if (bookingId) {
                                BookingService.get(bookingId).$promise
                                    .then((data) => {
                                        vm.booking = data;
                                        vm.editAllowed = $rootScope.authUser !== null && vm.booking.user.id == $rootScope.authUser.id;
                                        vm.owner = vm.booking.accommodation.user.id == $rootScope.authUser.id;
                                    })
                                    .catch(() => {
                                        console.log('Booking resource NOT found, redirecting to /error');
                                        $location.path('/error');
                                    });
                            } else {
                                vm.booking = new Booking();
                                vm.booking.user = $rootScope.authUser;
                                vm.booking.accommodation = accommodation;
                                vm.booking.status = "SUBMITTED";
                                vm.editAllowed = $rootScope.authenticated;
                            }
                        } else {
                            console.log('Accommodation resource NOT found (=false), redirecting to /error');
                            $location.path('/error');
                        }

                    })
                    .catch(() => {
                        console.log('Accommodation resource NOT found, redirecting to /error');
                        $location.path('/error');
                    });
            } else {
                console.log('AccommodationId request parameter NOT found, redirecting to /error');
                $location.path('/error');
            }

            const saveCallback = () => {
                $location.path(`/accommodations/${vm.booking.accommodation.id}/bookings/${vm.booking.id}`);
                vm.msg = 'SUCCESS - Entity created!';
            };
            const errorCallback = err => {
                console.log('Error: ', err);
                if (err) {
                    vm.msg = 'Error: ' + err.data.message + "\n";
                    err.data.errors.forEach(error => vm.msg = vm.msg + error.field + " " + error.defaultMessage + "\n");
                } else {
                    console.log('Error is undefined');
                    vm.msg = "Error message not available!";
                }
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
        }
    );