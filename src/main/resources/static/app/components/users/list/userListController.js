angular.module('app')
    .controller('UserListController', function (UserService, $route) {
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