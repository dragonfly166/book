package com.book.service;

import com.book.domain.AutoKey;
import com.book.domain.Book;
import com.book.domain.BookResult;
import com.book.domain.Order;
import com.book.domain.UserProfile;
import com.book.mapper.BookMapper;
import com.book.mapper.UserMapper;
import com.book.util.UserUtil;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sunlongfei
 */
@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Gson gson;

    /**
     * 补充用户信息
     */
    private List<BookResult> addUserProfiles(List<Book> books) {
        if (books == null) {
            return null;
        }

        List<BookResult> result = new ArrayList<>(books.size());
        for (Book book: books) {
            UserProfile user;
            String userStr = redisTemplate.opsForValue().get("user:profile:" + book.getPublisherId() + ":string");
            if (userStr == null) {
                user = userMapper.queryUserProfileById(book.getPublisherId());
                redisTemplate.opsForValue().set("user:profile:" + book.getPublisherId() + ":string",
                    gson.toJson(user), 3, TimeUnit.DAYS);
            }
            user = gson.fromJson(userStr, UserProfile.class);
            result.add(new BookResult(book.getId(), book.getName(), book.getDescription(),
                book.getImage(), user, book.getPrice(), book.getWatchNum(), book.getSold(), book.getCreateTime()));
        }
        return result;
    }

    /**
     * 获取旧书列表
     */
    public List<BookResult> getList(String orderBy, String order, String search, Boolean containSold) {
        if (search == null) {
            search = "";
        }
        search += "%" + search + "%";
        if (order == null) {
            order = "ASC";
        }
        if (!"DESC".equalsIgnoreCase(order)) {
            order = "ASC";
        }

        var bookList = bookMapper.queryBookList(orderBy, order, search, containSold);
        return addUserProfiles(bookList);
    }

    /**
     * 查看旧书
     */
    public BookResult getBookInfo(int id) {
        Book book = bookMapper.queryBookById(id);
        var bookResult = addUserProfiles(List.of(book)).get(0);
        bookMapper.addWatch(id);
        return bookResult;
    }

    /**
     * 购买旧书
     */
    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.SERIALIZABLE)
    public Order purchase(int bookId, String address) throws Exception {
        Book book = bookMapper.queryBookById(bookId);
        if (book == null) {
            throw new Exception("该书籍不存在");
        }
        if (book.getSold()) {
            throw new Exception("已售空");
        }

        bookMapper.updateSold(true);
        AutoKey key = new AutoKey();
        bookMapper.addOrder(UserUtil.getUserId(), bookId, book.getPrice(), address, key);
        return bookMapper.queryOrderById(key.getId());
    }

    /**
     * 发布旧书
     */
    public void publish(String name, String description, MultipartFile image, double price) {
        bookMapper.addBook(name, description, null, price);
    }
}
