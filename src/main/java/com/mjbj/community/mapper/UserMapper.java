package com.mjbj.community.mapper;

/*
 *使用mybatis，将数据库的表映射成类
 * */

import com.mjbj.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

//User表操作
//@Repository
@Mapper
public interface UserMapper {
    //向数据库插入user数据项
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values " +
            "(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    //通过token的值，从数据库中拿该项user数据
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
