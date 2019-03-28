// 购物车控制器
app.controller('cartController', function ($scope, $location, $controller, baseService) {
    // 继承baseController
    $controller('baseController', {$scope:$scope});
    
    // 定义ids数组
    $scope.ids = [];
    
    $scope.settle = function () {
        var ids = $scope.ids.join(",");
        window.open("/order/getOrderInfo.html?ids=" + ids);
    };
    

    /**
     * 选中商品
     */
    $scope.updateSelection = function($event, id, cart){
        var sellerId = cart.sellerId;
        var orderItems = cart.orderItems;
        // $event事件对象 (判断checkbox是否选中)
        //alert($event.target); // 获取dom元素
        if ($event.target.checked){ // 选中了checkbox
            if (cart.orderItems.length == $("input[name="+sellerId+"]:checked").length) {
                $(".cart-shop input[name=" + sellerId + "]").prop("checked", true);
            }
            for (var i = 0; i < orderItems.length; i++) {
                if (orderItems[i].itemId == id && $scope.ids.indexOf(id) == -1) {
                    // 往数组中添加元素
                    $scope.ids.push(id);
                    $scope.totalEntity.totalNum += orderItems[i].num;
                    $scope.totalEntity.totalMoney += orderItems[i].totalFee;
                }
            }
            if ($scope.ids.length == $(".goods-list input:checkbox").length) {
                $(".cart-main .yui3-u-1-4 input:checkbox").prop("checked", true);
                $(".cart-tool .select-all input:checkbox").prop("checked", true);
            }
        }else{
            // 没有选中checkbox
            // 从数据中删除元素
            // 获取一个元素在数组中的索引号
            var idx = $scope.ids.indexOf(id);
            // 删除元素
            // 第一个参数：索引号
            // 第二个参数：删除的个数
            $scope.ids.splice(idx,1);
            $(this).prop("checked", false);
            $(".cart-shop input[name=" + sellerId + "]").prop("checked", false);
            $(".cart-main .yui3-u-1-4 input:checkbox").prop("checked", false);
            $(".cart-tool .select-all input:checkbox").prop("checked", false);
            for (var i = 0; i < orderItems.length; i++) {
                if (orderItems[i].itemId == id) {
                    $scope.totalEntity.totalNum -= orderItems[i].num;
                    $scope.totalEntity.totalMoney -= orderItems[i].totalFee;
                }
            }
        }
    };

    /**
     * 选中商家
     */
    $scope.updateSelectionSeller = function($event, cart){
        var orderItems = cart.orderItems;
        var sellerId = cart.sellerId;

        if ($event.target.checked){
            $("input[name="+sellerId+"]").prop("checked", true);
            for (var i = 0; i < orderItems.length; i++) {
                $scope.updateSelection($event, orderItems[i].itemId, cart);
            }
        }else{
            for (var i = 0; i < orderItems.length; i++) {
                $scope.updateSelection($event, orderItems[i].itemId, cart);
                $("input[name="+sellerId+"]").prop("checked", false);
            }
        }
    };

    /**
     * 全选
     */
    $scope.updateSelectionAll = function ($event) {
        // 循环用户的购物车数组
        for (var i = 0; i < $scope.carts.length; i++){
            // 获取数组中的元素(一个商家的购物车)
            var cart = $scope.carts[i];
            $scope.updateSelectionSeller($event, cart, i);
            if ($event.target.checked) {
                $("input[type=checkbox]").prop("checked", true);
            } else {
                $("input[type=checkbox]").prop("checked", false);
            }
        }
    };

    // 查询用户的购物车
    $scope.findCart = function () {
        baseService.sendGet("/cart/findCart").then(function(response){
            // 获取响应数据
            $scope.carts = response.data;

            // 定义json对象封装统计的结果
            $scope.totalEntity = {totalNum : 0, totalMoney : 0};

            /**
            // 循环用户的购物车数组
            for (var i = 0; i < $scope.carts.length; i++){
                // 获取数组中的元素(一个商家的购物车)
                var cart = $scope.carts[i];
                // 循环该商家的购物车数组
                for (var j = 0; j < cart.orderItems.length; j++){
                    // 获取一个商品
                    var orderItem = cart.orderItems[j];

                    // 统计购买总数量
                    $scope.totalEntity.totalNum += orderItem.num;
                    // 统计购买总金额
                    $scope.totalEntity.totalMoney += orderItem.totalFee;
                }
            }
             */
        });
    };

    // 购买数量增减与删除
    $scope.addCart = function (itemId, num) {
        baseService.sendGet("/cart/addCart?itemId="
            + itemId + "&num=" + num).then(function(response){
            // 获取响应数据
            if (response.data){
                // 重新加载购物车数据
                $scope.findCart();
                $scope.ids = [];
            }
        });
    };
    
});