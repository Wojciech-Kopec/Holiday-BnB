angular.module('app')
    .controller('AccommodationListController', function (AccommodationService, $route) {
        const vm = this;

        vm.accommodations = AccommodationService.getAll();

        vm.search = (name, accommodationType, requiredGuestCount, minPricePerNight, maxPricePerNight, localization, amenityFilter1, amenityFilter2, amenityFilter3) => {

            if (name === "") name = null;
            if (accommodationType === "") accommodationType = null;
            if (requiredGuestCount === "") requiredGuestCount = null;
            if (minPricePerNight === "") minPricePerNight = null;
            if (maxPricePerNight === "") maxPricePerNight = null;
            if (localization === "") localization = null;
            let amenities = [];
            if (amenityFilter1) amenities.push(amenityFilter1);
            if (amenityFilter2) amenities.push(amenityFilter2);
            if (amenityFilter3) amenities.push(amenityFilter3);

            vm.accommodations = AccommodationService.getAll({
                name,
                accommodationType,
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

        vm.amenityTypes = [
            "WIFI",
            "KITCHEN",
            "TV",
            "POOL",
            "BACKYARD",
            "SAUNA",
            "PARKING",
            "TERRACE",
            "AC",
            "OTHER"
        ];

        vm.accommodationTypes = ["FLAT", "HOUSE", "CABIN", "RESORT", "SUITE", "ROOM"];

    });