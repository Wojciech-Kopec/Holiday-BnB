angular.module('app')
    .config(function ($routeProvider, $httpProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        $routeProvider

            .when('/accommodations', {
                templateUrl: 'app/components/accommodations/list/accommodationList.html',
                controller: 'AccommodationListController',
                controllerAs: 'ctrl'
            })
            .when('/accommodations/:accommodationId', {
                templateUrl: 'app/components/accommodations/edit/accommodationEdit.html',
                controller: 'AccommodationEditController',
                controllerAs: 'ctrl'
            })
            .when('/accommodation-add', {
                templateUrl: 'app/components/accommodations/edit/accommodationEdit.html',
                controller: 'AccommodationEditController',
                controllerAs: 'ctrl'
            })

            .when('/bookings', {
                templateUrl: 'app/components/bookings/list/bookingList.html',
                controller: 'BookingListController',
                controllerAs: 'ctrl'
            })
            .when('/accommodations/:accommodationId/bookings', {
                templateUrl: 'app/components/bookings/list/bookingList.html',
                controller: 'BookingListController',
                controllerAs: 'ctrl'
            })
            .when('/accommodations/:accommodationId/bookings/:bookingId', {
                templateUrl: 'app/components/bookings/edit/bookingEdit.html',
                controller: 'BookingEditController',
                controllerAs: 'ctrl'
            })
            .when('/accommodations/:accommodationId/booking-add', {
                templateUrl: 'app/components/bookings/edit/bookingEdit.html',
                controller: 'BookingEditController',
                controllerAs: 'ctrl'
            })

            .when('/users', {
                templateUrl: 'app/components/users/list/userList.html',
                controller: 'UserListController',
                controllerAs: 'ctrl'
            })
            .when('/users/:userId', {
                templateUrl: 'app/components/users/edit/userEdit.html',
                controller: 'UserEditController',
                controllerAs: 'ctrl'
            })
            .when('/register', {
                templateUrl: 'app/components/users/edit/userEdit.html',
                controller: 'UserEditController',
                controllerAs: 'ctrl'
            })
            .when('/users/:userId/accommodations', {
                templateUrl: 'app/components/accommodations/list/accommodationList.html',
                controller: 'AccommodationListController',
                controllerAs: 'ctrl'
            })
            .when('/users/:userId/bookings', {
                templateUrl: 'app/components/bookings/list/bookingList.html',
                controller: 'BookingListController',
                controllerAs: 'ctrl'
            })
            .when('/login', {
                templateUrl: 'login.html',
                controller: 'AuthenticationController',
                controllerAs: 'authCtrl'
            })
            .when('/error', {
                templateUrl: 'error.html',
            })
            .otherwise({
                redirectTo: '/accommodations'
            });
    });
