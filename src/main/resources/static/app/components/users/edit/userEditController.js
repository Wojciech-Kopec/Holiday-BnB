angular.module('app')
    .controller('UserEditController', function ($rootScope, $window, $routeParams, $location, $timeout, UserService, User) {
        $rootScope.authUser = JSON.parse($window.sessionStorage.getItem('authUser'));
        $rootScope.authenticated = JSON.parse($window.sessionStorage.getItem('authenticated'));

        const vm = this;
        const userId = $routeParams.userId;
        if (userId) {
            vm.user = UserService.get(userId);
            vm.editAllowed = $rootScope.authUser !== null && userId == $rootScope.authUser.id;
        } else {
            vm.user = new User();
            vm.editAllowed = !$rootScope.authenticated;
        }

        vm.saveUser = () => {
            UserService.save(vm.user)
                .then(saveCallback)
                .catch(errorCallback);
        };
        vm.updateUser = () => {
            UserService.update(vm.user)
                .then(updateCallback)
                .catch(errorCallback);
        };

        vm.getUsersAccommodations = () => {
            UserService.getAccommodations(vm.user)
                .then(accommodationsFetchCallback)
                .catch(errorCallback);
        };

        vm.getUsersBookings = () => {
            UserService.getBookings(vm.user)
                .then(bookingsFetchCallback)
                .catch(errorCallback);
        };

        const saveCallback = () => {
            $location.path(`/users/${vm.user.id}`);
            vm.msg = 'Save successful!'
        };

        const updateCallback = response => vm.msg = 'Update successful!';

        const accommodationsFetchCallback = response => vm.msg = 'User\'s accommodations fetch successful!';

        const bookingsFetchCallback = response => vm.msg = 'User\'s bookings fetch successful!';

        const errorCallback = err => {
            console.log(err);
            vm.msg = 'Error: ' + err;
        };

    });