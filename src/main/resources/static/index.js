//создаем приложение имя app, [тут dependency] указываем контроллер и функцию реализующую его
//scope - как глобальный контекст для JS и для html,
// если мы что-то создали например в html => то в js файле можем это у scope достать(и наоборот)
//http - модуль http для отправки запросов из фронта(https://docs.angularjs.org/api/ng/service/$http#general-usage)
//ngStorage - модуль локального хранилища, в браузере для данного приложения могут сохраняться какие-нибудь данные(это же типо депенденси для AngularJS)
//добавив ngStorage можем пользоваться локальным хранилищем(хэшмапа) $localStorage
angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8080/delivery';

    //ожидающие доставки
    $scope.showProcessedOrders = function () {
        $http.get(contextPath + '/orders/processed')
            .then(function (response) {
                $scope.ProcessedOrders = response.data;
            });
    }

    //активные(доставляются)
    $scope.showDeliveredOrders = function () {
        $http.get(contextPath + '/orders/delivered')
            .then(function (response) {
                $scope.DeliveredOrders = response.data;
            });
    }

    //выполненные заказы
    $scope.showCompletedOrders = function () {
        $http.get(contextPath + '/orders/completed')
            .then(function (response) {
                $scope.CompletedOrders = response.data;
            });
    }


    //закидывает в модальное окно редактирования - заказ
    $scope.getOrder = function (editableOrder) {
        $scope.editableOrder = editableOrder;
        //console.log($scope.editableOrder)
    }

    //редактировать заказ
    $scope.editStatusOrder = function () {
        console.log($scope.editableOrder + ' вызываем метод PUT')
        $http.put(contextPath + '/orders', $scope.editableOrder)
            .then(function (response) {
                $scope.showProcessedOrders();
                $scope.showDeliveredOrders();
                $scope.showCompletedOrders();
            });
    }

    //редактировать заказ
    $scope.deliveryConfirmation = function () {
        console.log($scope.editableOrder + ' вызываем метод PUT')
        $http.put(contextPath + '/orders', $scope.editableOrder)
            .then(function (response) {
                $scope.showProcessedOrders();
                $scope.showDeliveredOrders();
                $scope.showCompletedOrders();
            });
    }


    $scope.showProcessedOrders();
    $scope.showDeliveredOrders();
    $scope.showCompletedOrders();
});