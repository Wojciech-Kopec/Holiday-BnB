angular.module('app')
    .controller('AccommodationListController', function (AccommodationService, $route) {
        const vm = this;
        vm.accommodations = AccommodationService.getAll();

        const deleteCallback = () => {
            $route.reload();
        };

        const logErr = error => {
            console.log('ERROR', error);
        };

        vm.removeAccommodation = accommodation => {
            // if (popupService.showPopup('Do you want to delete this entry?')) {
            AccommodationService.remove(accommodation)
                .then(deleteCallback)
                .catch(logErr);
            // }
        };
    });