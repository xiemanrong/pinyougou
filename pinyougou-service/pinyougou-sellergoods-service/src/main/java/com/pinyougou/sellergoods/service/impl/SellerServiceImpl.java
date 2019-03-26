package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SellerMapper;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商家服务接口实现类
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-03<p>
 */
@Service(interfaceName = "com.pinyougou.service.SellerService")
@Transactional
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public void save(Seller seller) {
        try{
            // 设置商家的审核状态(未审核)
            seller.setStatus("0");
            // 设置入驻时间
            seller.setCreateTime(new Date());
            sellerMapper.insertSelective(seller);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 修改商家密码
     * @param seller
     */
    @Override
    public void updatePassword(Seller seller) {
        try{
            sellerMapper.updatePassById(seller);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 修改商家信息
     * @param seller
     */
    @Override
    public void update(Seller seller) {
        try{
            Seller seller1 = sellerMapper.selectByPrimaryKey(seller.getSellerId());
            seller1.setName(seller.getName());
            seller1.setMobile(seller.getMobile());
            seller1.setTelephone(seller.getTelephone());
            seller1.setAddressDetail(seller.getAddressDetail());
            seller1.setLinkmanName(seller.getLinkmanName());
            seller1.setLinkmanQq(seller.getLinkmanQq());
            seller1.setLinkmanMobile(seller.getLinkmanMobile());
            seller1.setLinkmanEmail(seller.getLinkmanEmail());
            seller1.setLicenseNumber(seller.getLicenseNumber());
            seller1.setTaxNumber(seller.getTaxNumber());
            seller1.setOrgNumber(seller.getOrgNumber());
            seller1.setLegalPerson(seller.getLegalPerson());
            seller1.setLegalPersonCardId(seller.getLegalPersonCardId());
            seller1.setBankName(seller.getBankName());
            seller1.setBankUser(seller.getBankUser());
            sellerMapper.deleteByPrimaryKey(seller1.getSellerId());
            sellerMapper.insertSelective(seller1);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public void deleteAll(Serializable[] ids) {

    }

    @Override
    public Seller findOne(Serializable id) {
        return sellerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }

    @Override
    public PageResult findByPage(Seller seller, int page, int rows) {
        try{
            // 开始分页
            PageInfo<Seller> pageInfo = PageHelper.startPage(page, rows).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    sellerMapper.findAll(seller);
                }
            });
            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /** 修改商家的审核状态 */
    public void updateStatus(String sellerId, String status){
        try{
            sellerMapper.updateStatus(sellerId, status);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 查询商家状态
     * @param username
     * @return
     */
    @Override
    public String findStatusByUsername(String username) {
        return sellerMapper.findStatusByUsername(username);
    }
}
