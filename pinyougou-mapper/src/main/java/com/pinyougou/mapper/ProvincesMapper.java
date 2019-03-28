package com.pinyougou.mapper;

import com.pinyougou.pojo.Provinces;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * ProvincesMapper 数据访问接口
 * @date 2019-02-27 09:55:07
 * @version 1.0
 */
public interface ProvincesMapper extends Mapper<Provinces>{

    @Select("select province as text from `tb_provinces`")
    List<Provinces> findprovince();

    @Select("selectcity as text from `tb_cities`")
    List<Provinces> findcity();

    @Select("select area as text from`tb_areas`")
    List<Provinces> findareas();
}