package com.pinyougou.mapper;

import com.pinyougou.pojo.Address;
import org.apache.ibatis.annotations.Delete;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;

/**
 * AddressMapper 数据访问接口
 * @date 2019-02-27 09:55:07
 * @version 1.0
 */
public interface AddressMapper extends Mapper<Address>{

    @Delete("DELETE FROM tb_address WHERE id = #{id}")
    void deleteById(Serializable id);
}