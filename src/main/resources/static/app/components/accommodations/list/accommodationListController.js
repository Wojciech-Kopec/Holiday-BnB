angular.module('app')
    .controller('AccommodationListController', function ($rootScope, $location, $window, $mdDialog, $scope, AccommodationService, $route, $routeParams) {
        $rootScope.authUser = JSON.parse($window.sessionStorage.getItem('authUser'));
        $rootScope.authenticated = JSON.parse($window.sessionStorage.getItem('authenticated'));

        const vm = this;
        AccommodationService.getAll().$promise
            .then(data => {
                vm.accommodations = data;

                const userId = $routeParams.userId;
                if (userId) {
                    vm.accommodations = vm.accommodations.filter(accommodation => accommodation.user.id == userId);
                }
            })
            .catch(err => {
                console.log('Could not fetch Accommodations');
                console.log(err);
                $location.path('/error');
            });

        vm.search = () => {
            let name, accommodationType, requiredGuestCount, minPricePerNight, maxPricePerNight, country, city;

            name = $scope.name !== "" ? $scope.name : null;
            accommodationType = $scope.accommodationType !== "" ? $scope.accommodationType : null;
            requiredGuestCount = $scope.requiredGuestCount !== "" ? $scope.requiredGuestCount : null;
            minPricePerNight = $scope.minPricePerNight !== "" ? $scope.minPricePerNight : null;
            maxPricePerNight = $scope.maxPricePerNight !== "" ? $scope.maxPricePerNight : null;
            country = $scope.country !== "" ? $scope.country : null;
            city = $scope.city !== "" ? $scope.city : null;

            let amenities = [];
            if ($scope.amenityFilter1) amenities.push($scope.amenityFilter1);
            if ($scope.amenityFilter2) amenities.push($scope.amenityFilter2);
            if ($scope.amenityFilter3) amenities.push($scope.amenityFilter3);

            vm.accommodations = AccommodationService.getAll({
                name,
                accommodationType,
                requiredGuestCount,
                minPricePerNight,
                maxPricePerNight,
                country,
                city,
                amenities
            })
        };

        vm.clearFilters = () => {
            $scope.name = null;
            $scope.accommodationType = null;
            $scope.requiredGuestCount = null;
            $scope.minPricePerNight = null;
            $scope.maxPricePerNight = null;
            $scope.country = null;
            $scope.city = null;
            $scope.amenityFilter1 = null;
            $scope.amenityFilter2 = null;
            $scope.amenityFilter3 = null;

            vm.search();
        };

        const deleteCallback = () => {
            $route.reload();
        };

        const logErr = error => {
            console.log('ERROR', error);
        };

        vm.removeAccommodation = accommodation => {
            AccommodationService.remove(accommodation)
                .then(deleteCallback)
                .catch(logErr);            // }
        };

        vm.removePopup = (event, accommodation) => {
            var confirm = $mdDialog.confirm()
                .title('Would you like to remove this entry?')
                .targetEvent(event)
                .ok('Remove')
                .cancel('Cancel');

            $mdDialog.show(confirm).then(function () {
                vm.removeAccommodation(accommodation)
            }, function () {
                console.log("Remove cancelled")
            });
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