package com.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.User;
import com.pinyougou.service.UserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户控制器
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-16<p>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(timeout = 10000)
    private UserService userService;


    /** 用户注册 */
    @PostMapping("/save")
    public boolean save(@RequestBody User user, String code){
        try{

            // 检验短信验证码
            boolean flag = userService.checkSmsCode(user.getPhone(), code);
            if (flag) {
                userService.save(user);
            }
            return flag;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /** 第一步短信验证码校验 */
    @PostMapping("/testCode")
    public boolean testCode( String phone, String code){
        try{

            // 检验短信验证码
            boolean flag = userService.testCode(phone, code);
            if (flag) {
                return flag;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }




    /** 第二步短信验证码校验,更改号码 */
    @PostMapping("/updatePhone")
    public boolean updatePhone(@RequestBody User user, String code, HttpServletRequest request){
        try{
            String username = request.getRemoteUser();
            // 检验短信验证码
            boolean flag = userService.checkSmsCode(user.getPhone(), code);
            if (flag) {
                userService.updatePhone(username,user.getPhone());
            }
            return flag;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }








    /** 密码设置 */
    @PostMapping("/update")
    public boolean update(@RequestBody User user){
        try{
            userService.update(user);

            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }


    /** 发送短信验证码 */
    @GetMapping("/sendSmsCode")
    public boolean sendSmsCode(String phone){
        try{
            return userService.sendSmsCode(phone);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /** 获取登录用户名 */
    @PostMapping("/findUser")
    public User findUser(){
       try {
           SecurityContext context = SecurityContextHolder.getContext();
           String loginName = context.getAuthentication().getName();
           User user = new User();
           user.setUsername(loginName);
           User user1 = userService.findUser(user).get(0);
           System.out.println(user1.getPhone());
           return user1;

       }catch (Exception e){
           throw new RuntimeException(e);
       }

    }



}
