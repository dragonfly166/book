package com.book.mapper;

import com.book.domain.Lack;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
