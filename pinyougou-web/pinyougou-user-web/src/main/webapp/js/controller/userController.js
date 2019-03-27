/** 定义控制器层 */
app.controller('userController', function($scope, $timeout, baseService){

    // 定义json对象
    $scope.user = {}

    // 用户注册
    $scope.save = function () {

        // 判断密码是否一致
        if ($scope.okPassword && $scope.user.password == $scope.okPassword) {
            // 发送异步请求
            baseService.sendPost("/user/save?code=" + $scope.code,
                $scope.user).then(function (response) {
                // 获取响应数据
                if (response.data) {
                    // 跳转到登录页面
                    // 清空表单数据
                    $scope.user = {};
                    $scope.okPassword = "";
                    $scope.code = "";
                } else {
                    alert("注册失败！");
                }
            });
        } else {
            alert("两次密码不一致！");

        }

    };

    // 判断验证码和短信验证码
    $scope.test = function () {

        // 判断密码是否一致
        if ($scope.okPassword && $scope.user.password == $scope.okPassword) {
            // 发送异步请求
            baseService.sendPost("/user/save?code=" + $scope.code,
                $scope.user).then(function (response) {
                // 获取响应数据
                if (response.data) {
                    // 跳转到登录页面
                    // 清空表单数据
                    $scope.user = {};
                    $scope.okPassword = "";
                    $scope.code = "";
                } else {
                    alert("注册失败！");
                }
            });
        } else {
            alert("两次密码不一致！");

        }

    };







            // 获取登录用户名
            $scope.showName = function () {
                baseService.sendGet("/user/showName").then(function (response) {
                    // 获取响应数据
                    $scope.loginName = response.data.loginName;
                });
            };


            //显示手机号码
            $scope.showPhone = function () {
                baseService.sendPost("/user/findUser").then(function (response) {
                    $scope.phone = response.data.phone;
                })

            };

            // 定义显示文本
            $scope.tipMsg = "获取短信验证码";
            $scope.flag = false;

            // 根据后端手机号码查询,发送短信验证码
            $scope.sendSmsCode1 = function () {

                baseService.sendPost("/user/findUser").then(function (response) {
                    $scope.phone = response.data.phone;
                })

                // 判断手机号码的有效性
                if ($scope.phone && /^1[3|4|5|7|8|9]\d{9}$/.test($scope.phone)) {
                    // 发送异步请求
                    baseService.sendGet("/user/sendSmsCode?phone="
                        + $scope.phone
                    ).then(function (response) {
                        // 获取响应数据
                        if (response.data) {
                            // 倒计时 (扩展)
                            $scope.flag = true;
                            // 调用倒计时方法
                            $scope.downcount(90);
                        } else {
                            alert("获取短信验证码失败！");
                        }
                    });
                } else {
                    alert("手机号码不正确！");
                }
            };


            //密码设置(修改)
            $scope.update = function () {
                // 判断密码是否一致
                if ($scope.confirm_password && $scope.user.password == $scope.confirm_password) {
                    // 发送异步请求
                    baseService.sendPost("/user/update",
                        $scope.user).then(function (response) {
                        // 获取响应数据
                        if (response.data) {
                            // 跳转到首页
                            // 清空表单数据
                            $scope.user = {};
                            $scope.user.username = "";
                            $scope.confirm_password = "";
                            alert("设置成功!")
                            //$scope.code = "";
                        } else {
                            alert("设置失败！");
                        }
                    });
                } else {
                    alert("两次密码不一致！");
                }
            };


            // 定义显示文本
            $scope.tipMsg = "获取短信验证码";
            $scope.flag = false;

            // 根据前端手机号码查询,发送短信验证码
            $scope.sendSmsCode = function () {

                // 判断手机号码的有效性
                if ($scope.user.phone && /^1[3|4|5|7|8|9]\d{9}$/.test($scope.user.phone)) {
                    // 发送异步请求
                    baseService.sendGet("/user/sendSmsCode?phone="
                        + $scope.user.phone).then(function (response) {
                        // 获取响应数据
                        if (response.data) {
                            // 倒计时 (扩展)
                            $scope.flag = true;
                            // 调用倒计时方法
                            $scope.downcount(90);
                        } else {
                            alert("获取短信验证码失败！");
                        }
                    });
                } else {
                    alert("手机号码不正确！");
                }
            };


            // 倒计时方法
            $scope.downcount = function (seconds) {
                if (seconds > 0) {
                    seconds--;
                    $scope.tipMsg = seconds + "秒，后重新获取";

                    // 开启定时器
                    $timeout(function () {
                        $scope.downcount(seconds);
                    }, 1000);
                } else {
                    $scope.tipMsg = "获取短信验证码";
                    $scope.flag = false;
                }
            };


});