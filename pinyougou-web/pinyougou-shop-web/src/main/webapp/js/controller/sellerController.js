/** 定义控制器层 */
app.controller('sellerController', function($scope, $controller, baseService, $http){

    /** 指定继承baseController */
    $controller('baseController',{$scope:$scope});

    /** 商家申请入驻或修改商家信息 */
    $scope.saveOrUpdate = function(seller){
        var url = "save"; // 添加
        // 判断品牌id
        if ($scope.seller.sellerId){ // 修改
            url = "update";
            $scope.seller.bankName = $scope.bank.name + " " + $scope.bank.sub;
        }

        /** 发送post请求 */
        baseService.sendPost("/seller/" + url, $scope.seller)
            .then(function(response){
                if (response.data) {
                    if (url == "save") {
                        /** 跳转到登录页面 */
                        location.href = "/shoplogin.html";
                    } else {
                        alert("保存成功！");
                        $scope.selectSeller();
                    }
                }else{
                    alert("入驻失败！");
                }
            });
    };

    // 定义密码对象
    $scope.pass = {oldPassword: "", newPassword: "", confirmPassword: ""};

    /**
     * 保存密码
     */
    $scope.savePass = function () {
        if ($scope.pass.newPassword != "" && $scope.pass.confirmPassword != "" && $scope.pass.oldPassword != "") {
            if ($scope.pass.newPassword == $scope.pass.confirmPassword){
                $scope.str = {oldPassword: $scope.pass.oldPassword, newPassword: $scope.pass.newPassword};
                baseService.sendPost("/seller/updatePass", $scope.str).then(function (response) {
                    if (response.data) {
                        alert("保存成功！");
                    } else {
                        alert("密码错误！");
                    }
                });
            } else {
                alert("确认密码不一致！");

            }
        } else {
            alert("请输入密码！");
        }
    };

    // 重置密码框
    $scope.reset = function () {
        $scope.pass.oldPassword = "";
        $scope.pass.newPassword = "";
        $scope.pass.confirmPassword = "";
    };

    $scope.bank = {name: "", sub: ""};
    /**
     * 查询商家信息
     */
    $scope.selectSeller = function () {
        baseService.sendGet("/seller/selectSeller").then(function (response) {
            $scope.seller = response.data;
            if ($scope.seller.bankName) {
                var arr = $scope.seller.bankName.split(" ");
                $scope.bank.name = arr[0];
                $scope.bank.sub = arr[1];
            } else {
                $scope.bank = {};
            }
        });
    };



    /** 查询条件对象 */
    $scope.searchEntity = {};
    /** 分页查询(查询条件) */
    $scope.search = function(page, rows){
        baseService.findByPage("/seller/findByPage", page,
			rows, $scope.searchEntity)
            .then(function(response){
                /** 获取分页查询结果 */
                $scope.dataList = response.data.rows;
                /** 更新分页总记录数 */
                $scope.paginationConf.totalItems = response.data.total;
            });
    };

    /** 显示修改 */
    $scope.show = function(entity){
       /** 把json对象转化成一个新的json对象 */
       $scope.entity = JSON.parse(JSON.stringify(entity));
    };

    /** 批量删除 */
    $scope.delete = function(){
        if ($scope.ids.length > 0){
            baseService.deleteById("/seller/delete", $scope.ids)
                .then(function(response){
                    if (response.data){
                        /** 重新加载数据 */
                        $scope.reload();
                    }else{
                        alert("删除失败！");
                    }
                });
        }else{
            alert("请选择要删除的记录！");
        }
    };
});