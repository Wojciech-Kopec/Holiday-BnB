angular.module('app')
    .controller('AccommodationListController', function (AccommodationService, $route) {
        const vm = this;

        vm.accommodations = AccommodationService.getAll();
        vm.amenities = AccommodationService.getAmenities();

        vm.search = (name, accommodationTypes, requiredGuestCount, minPricePerNight, maxPricePerNight, localization, amenities) => {

            if (name === "") name = null;
            if (accommodationTypes === "") accommodationTypes = null;
            if (requiredGuestCount === "") requiredGuestCount = null;
            if (minPricePerNight === "") minPricePerNight = null;
            if (maxPricePerNight === "") maxPricePerNight = null;
            if (localization === "") localization = null;
            if (amenities === "") amenities = null;

            vm.accommodations = AccommodationService.getAll({
                name,
                accommodationTypes,
                requiredGuestCount,
                minPricePerNight,
                maxPricePerNight,
                localization,
                amenities
            })
        };

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
                .catch(logErr);            // }
        };
    })
/*.directive('AmenitySelect', function () {
return {

}
})*/;