package com.pinyougou.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.ProvincesMapper;
import com.pinyougou.pojo.Provinces;
import com.pinyougou.service.ProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service(interfaceName = "com.pinyougou.service.ProvincesService")
@Transactional
public class ProvincesServiceImpl implements ProvincesService{

    @Autowired
    private ProvincesMapper provincesMapper;

    @Override
    public void save(Provinces provinces) {

    }

    @Override
    public void update(Provinces provinces) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public void deleteAll(Serializable[] ids) {

    }

    @Override
    public Provinces findOne(Serializable id) {
        return null;
    }

    @Override
    public List<Provinces> findAll() {
        return null;
    }

    @Override
    public List<Provinces> findByPage(Provinces provinces, int page, int rows) {
        return null;
    }

    @Override
    public List<Provinces> findprovince() {
        try{
            /** 创建ItemCat封装查询条件 */
            return provincesMapper.findprovince();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Provinces> findcity() {
        try{
            /** 创建ItemCat封装查询条件 */
            return provincesMapper.findcity();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Provinces> findareas() {
        try{
            /** 创建ItemCat封装查询条件 */
            return provincesMapper.findareas();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }


}
