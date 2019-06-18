angular.module('app')
    .controller('BookingListController', function (BookingService, $route, $routeParams, $mdDialog) {
        const vm = this;
        vm.bookings = BookingService.getAll();

        const userId = $routeParams.userId;
        const accommodationId = $routeParams.accommodationId;
        if (userId) {
            vm.bookings = vm.bookings.filter(booking => booking.user.id === userId);
        } else if (accommodationId) {
            vm.bookings = vm.bookings.filter(booking => booking.accommodation.id === accommodationId);
        }

        const deleteCallback = () => {
            $route.reload();
        };

        const logErr = error => {
            console.log('ERROR', error);
        };

        vm.removeBooking = booking => {
            BookingService.remove(booking)
                .then(deleteCallback)
                .catch(logErr);
        };

        vm.removePopup = (event, booking) => {
            var confirm = $mdDialog.confirm()
                .title('Would you like to remove this entry?')
                .targetEvent(event)
                .ok('Remove')
                .cancel('Cancel');

            $mdDialog.show(confirm).then(function () {
                vm.removeBooking(booking)
            }, function () {
                console.log("Remove cancelled")
            });
        };
    });