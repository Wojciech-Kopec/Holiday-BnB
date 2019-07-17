angular.module('app')
    .controller('UserEditController', function ($rootScope, $window, $routeParams, $route, $location, $timeout, UserService, User) {
        $rootScope.authUser = JSON.parse($window.sessionStorage.getItem('authUser'));
        $rootScope.authenticated = JSON.parse($window.sessionStorage.getItem('authenticated'));

        const vm = this;
        const userId = $routeParams.userId;

        if (userId) {
            UserService.get(userId).$promise
                .then((data) => {
                    vm.user = data;
                    vm.editAllowed = $rootScope.authUser !== null && userId == $rootScope.authUser.id;
                })
                .catch(() => {
                    console.log('User resource NOT found, redirecting to /error');
                    $location.path('/error')
                })
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

        const saveCallback = () => {
            vm.msg = 'Save successful!';
            $location.path(`/login`);
        };

        const updateCallback = response => vm.msg = 'Update successful!';

        const errorCallback = err => {
            console.log('Error: ', err);
            if (err) {
                vm.msg = 'Error: ' + err.data.message + "\n";
                err.data.errors.forEach(error => vm.msg = vm.msg + error.field + " " + error.defaultMessage + "\n");
            } else {
                console.log('Error is undefined');
                vm.msg = "Error message not available!";
            }
        };
    });