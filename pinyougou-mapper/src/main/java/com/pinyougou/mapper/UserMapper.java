package com.pinyougou.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.User;

/**
 * UserMapper 数据访问接口
 * @date 2019-02-27 09:55:07
 * @version 1.0
 */
public interface UserMapper extends Mapper<User>{

    //登录成功后查询用户名
//    @Select("select username from tb_user where username=#{username} and password=#{password}")
//    public String getUsername(@Param("username") String username, @Param("password") String password);

    //登陆成功后查询用户的所有信息
//    @Select("select * from tb_user where username=#{username}")
//    public User userinfor(@Param("username") String username);

    //用户信息的更新操作
//    void updatePassword(@Param("id")Long id,
//                        @Param("username") String username,
//                        @Param("password") String password);
}