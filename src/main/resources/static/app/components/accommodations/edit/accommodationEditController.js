angular.module('app')
    .controller('AccommodationEditController', function ($rootScope, $window, $routeParams, $location, $timeout, AccommodationService, Accommodation) {
        $rootScope.authUser = JSON.parse($window.sessionStorage.getItem('authUser'));
        $rootScope.authenticated = JSON.parse($window.sessionStorage.getItem('authenticated'));

        if(!$rootScope.authenticated)
            $location.path('/login');

        const vm = this;
        const accommodationId = $routeParams.accommodationId;
        if (accommodationId) {
            vm.accommodation = AccommodationService.get(accommodationId);
        } else {
            vm.accommodation = new Accommodation();
            vm.accommodation.amenities = [];
            vm.accommodation.user = $rootScope.authUser;
        }

        vm.iteration = [0, 1, 2, 3, 4];

        const saveCallback = () => {
            $location.path(`/accommodation-edit/${vm.accommodation.id}`);
        };
        const updateCallback = response => vm.msg = 'SUCCESS!';

        const errorCallback = err => {
            vm.msg = `Saving error!\n ${err.data.message}`;
        };

        vm.saveAccommodation = () => {
            var amenityArray = [];
            var index;

            for (index = 0; index < vm.accommodation.amenities.length; index++) {
                let amenity = vm.accommodation.amenities[index];
                amenity = {...amenity, accommodationId: null};
                amenityArray.push(amenity);
            }
            vm.accommodation.amenities = amenityArray;

            AccommodationService.save(vm.accommodation)
                .then(saveCallback)
                .catch(errorCallback);
        };
        vm.updateAccommodation = () => {
            AccommodationService.update(vm.accommodation)
                .then(updateCallback)
                .catch(errorCallback);
        };

    });