package com.book.mapper;

import com.book.domain.User;
import com.book.domain.UserCoreInfo;
import com.book.domain.UserProfile;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author sunlongfei
 */
@Mapper
public interface UserMapper {

    /**
     * 查询指定用户信息
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User queryUserById(int id);

    /**
     * 查询所有用户信息
     */
    @Select("SELECT * FROM user")
    List<User> queryAllUser();

    /**
     * 查询是否存在该用户名
     */
    @Select("SELECT COUNT(*) FROM user WHERE username = #{username}")
    boolean hasExisted(String username);

    /**
     * 添加用户
     */
    @Insert("INSERT INTO user(username, password) VALUES(#{username}, #{password})")
    void addUser(String username, String password);

    /**
     * 查询用户关键信息
     */
    @Select("SELECT id, identity FROM user WHERE username = #{username} AND password = #{password}")
    UserCoreInfo queryCoreInfo(String username, String password);

    /**
     * 更新用户信息
     */
    @Update("<script>UPDATE user SET id = id "
                + "<if test='user.username!=null'>,username = #{user.username} </if>"
                + "<if test='user.password!=null'>,password = #{user.password} </if>"
                + "<if test='user.email!=null'>,email = #{user.email} </if>"
                + "<if test='user.sex!=null'>,sex = #{user.sex} </if>"
                + "<if test='user.avatar!=null'>,avatar = #{user.avatar} </if>"
                + "WHERE id = #{id}</script>")
    void updateUser(User user,int id);

    /**
     * 查询用户基本信息
     */
    @Select("SELECT id, username, avatar FROM user WHERE id = #{id}")
    UserProfile queryUserProfileById(int id);
}
