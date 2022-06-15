package com.book.mapper;

import com.book.domain.Lack;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author sunlongfei
 */
@Mapper
public interface LackMapper {

    /**
     * 添加缺书信息
     */
    @Insert("INSERT INTO lack (book_name, publisher_id) VALUES (#{bookName}, #{publisherId})")
    void addLackBook(String bookName, int publisherId);

    /**
     * 查询缺书信息
     */
    @Select("<script>SELECT * FROM lack "
                + "<if test='containSolved!=true'>WHERE is_solved = 0 </if></script>")
    List<Lack> queryLacks(Boolean containSolved);

    /**
     * 查询指定id缺书信息
     */
    @Select("SELECT * FROM lack WHERE id = #{id}")
    Lack queryLackById(int id);

    /**
     * 更新缺书请求完成信息
     */
    @Update("UPDATE lack SET is_solved = #{isSolved} WHERE id = #{id}")
    void updateSolved(boolean isSolved, int id);

    /**
     * 删除求购信息
     */
    @Delete("DELETE FROM lack WHERE id = #{id}")
    void deleteLack(Integer id);
}
