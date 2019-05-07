angular.module('app')
    .controller('AccommodationEditController', function ($routeParams, $location, $timeout, AccommodationService, Accommodation) {
        const vm = this;
        const accommodationId = $routeParams.accommodationId;
        if (accommodationId)
            vm.accommodation = AccommodationService.get(accommodationId);
        else
            vm.accommodation = new Accommodation();

        const saveCallback = () => {
            $location.path(`/accommodation-edit/${vm.accommodation.id}`);
        };
        const errorCallback = err => {
            vm.msg = `Błąd zapisu: ${err.data.message}`;
        };

        vm.saveAccommodation = () => {
            AccommodationService.save(vm.accommodation)
                .then(saveCallback)
                .catch(errorCallback);
        };

        const updateCallback = response => vm.msg = 'Zapisano zmiany';
        vm.updateAccommodation = () => {
            AccommodationService.update(vm.accommodation)
                .then(updateCallback)
                .catch(errorCallback);
        };

    });