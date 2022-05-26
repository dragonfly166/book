package com.book.mapper;

import com.book.domain.AutoKey;
import com.book.domain.Book;
import com.book.domain.Order;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author sunlongfei
 */
@Mapper
public interface BookMapper {

    /**
     * 查询旧书列表
     */
    @Select("<script>SELECT * FROM book WHERE name LIKE #{search} "
                + "<if test='containSold!=true'>AND sold = 0 </if>"
                + "<if test='orderBy!=null'>ORDER BY ${orderBy} ${order} </if></script>")
    List<Book> queryBookList(String orderBy, String order, String search, Boolean containSold);

    /**
     * 根据id查询旧书信息
     */
    @Select("")
    Book queryBookById(int id);

    /**
     * 更新指定id旧书的阅读量（+1）
     */
    @Update("")
    void addWatch(int id);

    /**
     * 更新书籍销售情况sold字段，值由参数提供
     */
    @Update("")
    void updateSold(boolean sold);

    /**
     * 添加新的order
     */
    @Insert("")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "key.id")
    void addOrder(int userId, int bookId, double price, String address, AutoKey key);

    /**
     * 查询指定id的订单
     */
    @Select("")
    Order queryOrderById(int id);

    /**
     * 查看指定用户id的所有订单
     */
    @Select("")
    List<Order> queryOrdersByUserId(int userId);

    /**
     * 查看所有订单，不限用户
     */
    @Select("")
    List<Order> queryAllOrders();

    /**
     * 添加新的书籍
     */
    @Insert("")
    void addBook(String name, String description, String imageUrl, double price);
}
