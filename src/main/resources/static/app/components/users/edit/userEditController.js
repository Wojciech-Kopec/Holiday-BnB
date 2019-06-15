angular.module('app')
    .controller('UserEditController', function ($rootScope, $window, $routeParams, $location, $timeout, UserService, User) {
        $rootScope.authUser = JSON.parse($window.sessionStorage.getItem('authUser'));
        $rootScope.authenticated = JSON.parse($window.sessionStorage.getItem('authenticated'));

        const vm = this;
        const userId = $routeParams.userId;
        if (userId) {
            vm.user = UserService.get(userId);
            if (userId === $rootScope.authUser.id)
                vm.editAllowed = true;
        } else
            vm.user = new User();

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
            $location.path(`/user-edit/${vm.user.id}`);
            vm.msg = 'Save successful!'
        };

        const updateCallback = response => vm.msg = 'Update successful!';

        const errorCallback = err => {
            vm.msg = 'Saving Error: ${err.data.message}';
        };

    });