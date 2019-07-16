angular.module('app')
    .constant('LOGIN_ENDPOINT', API_ROOT + '/login')
    .service('AuthenticationService', function ($http, $window, $rootScope, $route, $q, LOGIN_ENDPOINT) {
        this.authenticate = function (username, password, successCallback) {
            var deferred = $q.defer();
            var authHeader = {Authorization: 'Basic ' + btoa(username + ':' + password)};
            var config = {headers: authHeader};
            $http
                .post(LOGIN_ENDPOINT, {}, config)
                .then(function success(value) {
                    $http.defaults.headers.common.Authorization = authHeader.Authorization;
                    successCallback();
                    deferred.resolve();
                }, function error(reason) {
                    console.log('Authentication error');
                    console.log(reason);
                    deferred.reject(reason);
                });
            return deferred.promise;
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

        function authSuccess() {
            $rootScope.authenticated = true;
            $window.sessionStorage.setItem('authenticated', JSON.stringify($rootScope.authenticated));

            $rootScope.authUser = vm.users.find(user => user.username === vm.credentials.username);
            $window.sessionStorage.setItem('authUser', JSON.stringify($rootScope.authUser));

            $route.reload();
        }

        vm.login = function () {
            AuthenticationService.authenticate(vm.credentials.username, vm.credentials.password, authSuccess)
                .catch(() => vm.msg = "Login and/or password is incorrect. Try again");
        };

        vm.logout = function () {
            AuthenticationService.removeAuthentication();
        };
    });