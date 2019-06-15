angular.module('app')
    .config(function ($routeProvider, $httpProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        $routeProvider

            .when('/accommodations', {
                templateUrl: 'app/components/accommodations/list/accommodationList.html',
                controller: 'AccommodationListController',
                controllerAs: 'ctrl'
            })
            .when('/accommodation-edit/:accommodationId', {
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
            .when('/booking-edit/:bookingId', {
                templateUrl: 'app/components/bookings/edit/bookingEdit.html',
                controller: 'BookingEditController',
                controllerAs: 'ctrl'
            })
            .when('/booking-add', {
                templateUrl: 'app/components/bookings/edit/bookingEdit.html',
                controller: 'BookingEditController',
                controllerAs: 'ctrl'
            })

            .when('/users', {
                templateUrl: 'app/components/users/list/userList.html',
                controller: 'UserListController',
                controllerAs: 'ctrl'
            })
            .when('/user-edit/:userId', {
                templateUrl: 'app/components/users/edit/userEdit.html',
                controller: 'UserEditController',
                controllerAs: 'ctrl'
            })
            .when('/user-add', {
                templateUrl: 'app/components/users/edit/userEdit.html',
                controller: 'UserEditController',
                controllerAs: 'ctrl'
            })
            .when('/login', {
                templateUrl: 'login.html',
                controller: 'AuthenticationController',
                controllerAs: 'authCtrl'
            })
            .otherwise({
                redirectTo: '/accommodations'
            });
    });
