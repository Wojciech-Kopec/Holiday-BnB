angular.module('app')
    .controller('AccommodationEditController', function ($rootScope, $window, $routeParams, $location, $timeout, AccommodationService, Accommodation) {
        $rootScope.authUser = JSON.parse($window.sessionStorage.getItem('authUser'));
        $rootScope.authenticated = JSON.parse($window.sessionStorage.getItem('authenticated'));

        const vm = this;
        const accommodationId = $routeParams.accommodationId;

        if (accommodationId) {
            AccommodationService.get(accommodationId).$promise
                .then((data) => {
                    vm.accommodation = data;
                    vm.editAllowed = $rootScope.authUser !== null && vm.accommodation.user.id == $rootScope.authUser.id;
                })
                .catch(() => {
                    console.log('Accommodation resource NOT found, redirecting to /error');
                    $location.path('/error')
                });

        } else {
            vm.accommodation = new Accommodation();
            vm.accommodation.amenities = [];
            vm.accommodation.user = $rootScope.authUser;
            vm.editAllowed = $rootScope.authenticated;
        }

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

        const saveCallback = () => {
            $location.path(`/accommodations/${vm.accommodation.id}`);
            vm.msg = 'SUCCESS - Entity created!';
        };

        const updateCallback = response => vm.msg = 'SUCCESS!';

        const errorCallback = err => {
            console.log('Error: ', err);
            if(err) {
                vm.msg = 'Error: ' + err.data.message + "\n";
                err.data.errors.forEach(error => vm.msg = vm.msg + error.field + " " + error.defaultMessage + "\n");
            } else {
                console.log('Error is undefined');
                vm.msg = "Error message not available!";
            }
            };

        vm.iteration = [0, 1, 2, 3, 4];


    });