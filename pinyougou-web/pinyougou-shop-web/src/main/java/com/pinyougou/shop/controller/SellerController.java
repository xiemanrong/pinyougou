package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 商家控制器
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-03<p>
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

    // @Autowired spring容器中的bean
    @Reference(timeout = 10000)
    private SellerService sellerService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /** 商家入驻 */
    @PostMapping("/save")
    public boolean save(@RequestBody Seller seller){
        try{
            // 密码加密
            String password = passwordEncoder.encode(seller.getPassword());
            seller.setPassword(password);
            sellerService.save(seller);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /** 修改商家信息 */
    @PostMapping("/update")
    public boolean update(@RequestBody Seller seller){
        try{
            sellerService.update(seller);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    @GetMapping("/selectSeller")
    public Seller selectSeller() {
        // 获取安全上下文对象
        SecurityContext context = SecurityContextHolder.getContext();
        // 获取登录用户名
        String loginName = context.getAuthentication().getName();
        Seller seller = sellerService.findOne(loginName);
        seller.setPassword("");
        return seller;
    }

    /**
     * 更改用户密码
     * @param pass
     * @return
     */
    @PostMapping("/updatePass")
    public boolean updatePass(@RequestBody HashMap<String, String> pass) {
        try {
            Seller seller1 = selectSeller();
            Seller seller = sellerService.findOne(seller1.getSellerId());
            boolean matches = passwordEncoder.matches(pass.get("oldPassword"), seller.getPassword());
            if (!matches) {
                return false;
            }
            seller.setPassword(passwordEncoder.encode(pass.get("newPassword")));
            sellerService.updatePassword(seller);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
