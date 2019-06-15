angular.module('app')
    .constant('LOGIN_ENDPOINT', '/login')
    .service('AuthenticationService', function ($http, $window, $rootScope, $route, LOGIN_ENDPOINT) {
        this.authenticate = function (credentials, successCallback) {
            var authHeader = {Authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)};
            var config = {headers: authHeader};
            $http
                .post(LOGIN_ENDPOINT, {}, config)
                .then(function success(value) {
                    $http.defaults.headers.post.Authorization = authHeader.Authorization;
                    successCallback();
                }, function error(reason) {
                    console.log('Login error');
                    console.log(reason);
                });
        };
        this.removeAuthentication = function () {
            delete $http.defaults.headers.post.Authorization;
            $window.sessionStorage.removeItem('authUser');
            $window.sessionStorage.removeItem('authenticated');
            $rootScope.authUser = null;
            $rootScope.authenticated = false;
            $route.reload();
        };
    })
    .controller('AuthenticationController', function ($rootScope, $window, $location, $route, AuthenticationService, UserService) {
        $rootScope.authUser = () => {
            let authUser = $window.sessionStorage.getItem('authUser');
            if (authUser)
                return JSON.parse(authUser);
            else
                return null;
        };
        $rootScope.authenticated = JSON.parse($window.sessionStorage.getItem('authenticated'));

        var vm = this;
        vm.users = UserService.getAll();
        vm.credentials = {};

        function loginSuccess() {
            $rootScope.authenticated = true;
            $window.sessionStorage.setItem('authenticated', JSON.stringify($rootScope.authenticated));

            $rootScope.authUser = vm.users.find(user => user.username === vm.credentials.username);
            $window.sessionStorage.setItem('authUser', JSON.stringify($rootScope.authUser));

            $route.reload();
        }

        vm.login = function () {
            AuthenticationService.authenticate(vm.credentials, loginSuccess);
        };

        vm.logout = function () {
            AuthenticationService.removeAuthentication();
        };
    });