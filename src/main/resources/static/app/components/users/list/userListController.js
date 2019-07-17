angular.module('app')
    .controller('UserListController', function ($rootScope, $window, UserService, $route, $mdDialog) {
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
            UserService.remove(user)
                .then(deleteCallback)
                .catch(logErr);
        };

        vm.removePopup = (event, user) => {
            let confirm = $mdDialog.confirm()
                .title('Would you like to remove this entry?')
                .targetEvent(event)
                .ok('Remove')
                .cancel('Cancel');

            $mdDialog.show(confirm).then(function() {
                vm.removeUser(user)
            }, function() {
                console.log("Remove cancelled")
            });
        };
    });