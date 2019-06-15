angular.module('app')
    .controller('UserListController', function ($rootScope, $window, UserService, $route) {
        let authUser = $window.sessionStorage.getItem('authUser');

        if (authUser)
            $rootScope.authUser = JSON.parse(authUser);
        else
            $rootScope.authUser = null;

        $rootScope.authenticated = JSON.parse($window.sessionStorage.getItem('authenticated'));

        const vm = this;
        vm.users = UserService.getAll();

        vm.search = username => {
            vm.users = UserService.getAll({username});
        };

        const deleteCallback = () => {
            $route.reload();
        };

        const logErr = error => {
            console.log('ERROR', error);
        };

        vm.removeUser = user => {
            // if (popupService.showPopup('Do you want to delete this entry?')) {
            UserService.remove(user)
                .then(deleteCallback)
                .catch(logErr);
            // }
        };
    });