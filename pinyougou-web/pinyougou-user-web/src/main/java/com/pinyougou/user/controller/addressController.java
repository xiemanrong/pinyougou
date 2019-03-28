package com.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Address;
import com.pinyougou.service.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class addressController {

    @Reference(timeout = 10000)
    private AddressService addressService;

    //查询全部
    @GetMapping("/findAll")
    public List<Address> findAll(){
        return addressService.findAll();
    }

    /** 添加 */
    @PostMapping("/save")
    public boolean save(@RequestBody Address address){
        try{
            addressService.save(address);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /** 删除 */
    @GetMapping("/delete")
    public boolean delete(Long id){
        try{
            addressService.delete(id);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
