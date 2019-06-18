angular.module('app')
    .controller('AccommodationEditController', function ($rootScope, $window, $routeParams, $location, $timeout, AccommodationService, Accommodation) {
        $rootScope.authUser = JSON.parse($window.sessionStorage.getItem('authUser'));
        $rootScope.authenticated = JSON.parse($window.sessionStorage.getItem('authenticated'));


        const vm = this;
        const accommodationId = $routeParams.accommodationId;
        if (accommodationId) {
            let promise = AccommodationService.get(accommodationId);
            console.log(promise);
            promise.$promise.then((result) => vm.accommodation = result.value);
            console.log(vm.accommodation);
            if (vm.accommodation) {
                vm.editAllowed = $rootScope.authUser !== null && vm.accommodation.user.id == $rootScope.authUser.id;
            } else
                $location.path('/error');
        } else {
            vm.accommodation = new Accommodation();
            vm.accommodation.amenities = [];
            vm.accommodation.user = $rootScope.authUser;
            vm.editAllowed = $rootScope.authenticated;
        }

        vm.iteration = [0, 1, 2, 3, 4];

        const saveCallback = () => {
            $location.path(`/accommodations/${vm.accommodation.id}`);
        };
        const updateCallback = response => vm.msg = 'SUCCESS!';

        const errorCallback = err => {
            vm.msg = `Saving error!\n ${err.data.message}`;
        };

        vm.saveAccommodation = () => {
            let amenityArray = [];
            let index;

            for (index = 0; index < vm.accommodation.amenities.length; index++) {
                let amenity = vm.accommodation.amenities[index];
                // amenity = {...amenity, accommodationId: null};
                if (amenity.type !== null)
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