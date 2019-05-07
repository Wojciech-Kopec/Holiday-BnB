angular.module('app')
    .controller('BookingListController', function (BookingService, $route) {
        const vm = this;
        vm.bookings = BookingService.getAll();

        const deleteCallback = () => {
            $route.reload();
        };

        const logErr = error => {
            console.log('ERROR', error);
        };

        vm.removeBooking = booking => {
            // if (popupService.showPopup('Do you want to delete this entry?')) {
            BookingService.remove(booking)
                .then(deleteCallback)
                .catch(logErr);
            // }
        };
    });