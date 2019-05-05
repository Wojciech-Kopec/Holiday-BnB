angular.module('app')
.constant('USER_ENDPOINT', '/api/users/:id')
.constant('USER_ACCOMMODATIONS_ENDPOINT', '/api/users/:id/accommodations')
.constant('USER_BOOKINGS_ENDPOINT', '/api/users/:id/bookings')
.factory('User', function($resource, USER_ENDPOINT, USER_ACCOMMODATIONS_ENDPOINT) {
    return $resource(USER_ENDPOINT, { id: '@_id' }, {
        update: {
            method: 'PUT'
        },
        // remove: {
        //   method: 'DELETE',
        // },
        getAccommodations: {
            method: 'GET',
            url: USER_ACCOMMODATIONS_ENDPOINT,
            params: {id: '@id'},
            isArray: true
        }
    });
})
.service('UserService', function(User) {
    this.getAll = params => User.query(params);
    this.get = index => User.get({id: index});
    this.getAccommodations = index => User.getAccommodations({id: index});
    this.save = user => user.$save();
    this.update = user => user.$update({id: user.id});
    this.remove = user => user.$remove({id: user.id});
});