package com.pinyougou.mapper;

import com.pinyougou.pojo.Seller;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * SellerMapper 数据访问接口
 * @date 2019-02-27 09:55:07
 * @version 1.0
 */
public interface SellerMapper extends Mapper<Seller>{

    /** 多条件分页查询商家 */
    List<Seller> findAll(Seller seller);

    /** 修改商家的审核状态 */
    @Update("UPDATE tb_seller SET STATUS = #{status} WHERE seller_id = #{sellerId}")
    void updateStatus(@Param("sellerId") String sellerId, @Param("status") String status);

    /**
     * 查询商家状态
     * @param username
     * @return
     */
    @Select("SELECT status FROM tb_seller where seller_id = #{username}")
    String findStatusByUsername(String username);

    /**
     * 修改商家密码
     * @param seller
     */
    @Update("UPDATE tb_seller SET password = #{password} where seller_id = #{sellerId}")
    void updatePassById(Seller seller);
}