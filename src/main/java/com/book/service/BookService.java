package com.book.service;

import com.book.domain.AutoKey;
import com.book.domain.Book;
import com.book.domain.BookResult;
import com.book.domain.Data;
import com.book.domain.Order;
import com.book.domain.OrderResult;
import com.book.domain.UserProfile;
import com.book.mapper.BookMapper;
import com.book.mapper.UserMapper;
import com.book.util.UserUtil;
import com.google.gson.Gson;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sunlongfei
 */
@Slf4j
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

    @Autowired
    private ImageService imageService;

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
            String userStr = null;
            try {
                userStr = redisTemplate.opsForValue().get("user:profile:" + book.getPublisherId() + ":string");
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
            if (userStr == null) {
                user = userMapper.queryUserProfileById(book.getPublisherId());
                try {
                    redisTemplate.opsForValue().set("user:profile:" + book.getPublisherId() + ":string",
                        gson.toJson(user), 3, TimeUnit.DAYS);
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            } else {
                user = gson.fromJson(userStr, UserProfile.class);
            }
            result.add(new BookResult(book.getId(), book.getName(), book.getDescription(),
                book.getImage(), user, book.getPrice(), book.getWatchNum(), book.getSold(), book.getCreateTime()));
        }
        return result;
    }

    /**
     * 添加用户与书籍数据
     */
    private List<OrderResult> addUsersAndBooks(List<Order> orders) {
        if (orders == null) {
            return null;
        }

        List<OrderResult> result = new ArrayList<>(orders.size());
        for (Order order: orders) {
            UserProfile user;
            Book book;
            String str = null;

            try {
                str = redisTemplate.opsForValue().get("user:profile:" + order.getUserId() + ":string");
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
            if (str == null) {
                user = userMapper.queryUserProfileById(order.getUserId());
                try {
                    redisTemplate.opsForValue().set("user:profile:" + order.getUserId() + ":string",
                        gson.toJson(user), 3, TimeUnit.DAYS);
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            }else {
                user = gson.fromJson(str, UserProfile.class);
            }

            str = null;
            try {
                str = redisTemplate.opsForValue().get("book:info:" + order.getBookId() + ":string");
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
            if (str == null) {
                book = bookMapper.queryBookById(order.getBookId());
                try {
                    redisTemplate.opsForValue().set("book:info:" + order.getBookId() + ":string",
                        gson.toJson(book), 3, TimeUnit.DAYS);
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            } else {
                book = gson.fromJson(str, Book.class);
            }

            OrderResult item = new OrderResult(order.getId(), order.getCost(), order.getCreateTime(),
                order.getIsSuccess(), order.getAddress());
            item.setBook(book);
            item.setUser(user);
            result.add(item);
        }
        return result;
    }

    /**
     * 获取旧书列表
     */
    public List<BookResult> getList(String orderBy, String order, String search, Boolean containSold) {
        if ((!"watch_num".equals(orderBy) && !"price".equals(orderBy))) {
            orderBy = "create_time";
        }
        if (search == null) {
            search = "";
        }
        search = "%" + search + "%";
        if (order == null) {
            order = "DESC";
        }
        if (!"ASC".equalsIgnoreCase(order)) {
            order = "DESC";
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

        bookMapper.updateSold(true, book.getId());
        AutoKey key = new AutoKey();
        bookMapper.addOrder(UserUtil.getUserId(), bookId, book.getPrice(), address, key);
        return bookMapper.queryOrderById(key.getId());
    }

    /**
     * 发布旧书
     */
    public void publish(String name, String description, MultipartFile image, double price)
        throws IOException {
        String url = null;
        if (image != null) {
            url = imageService.saveImage(image);
        }
        bookMapper.addBook(name, description, url, price, UserUtil.getUserId());
    }

    /**
     * 查询我的订单
     */
    public List<OrderResult> getMyOrders() {
        var myOrders = bookMapper.queryOrdersByUserId(UserUtil.getUserId());
        return addUsersAndBooks(myOrders);
    }

    /**
     * 查询所有订单
     */
    public List<OrderResult> getAllOrders() {
        var orders = bookMapper.queryAllOrders();
        return addUsersAndBooks(orders);
    }

    /**
     * 获取数据
     */
    public Data getData() {
        var userNum = bookMapper.queryUserNum();
        var bookData = bookMapper.queryBookData();
        var orderData = bookMapper.queryOrderData();
        return new Data(userNum, (Long) bookData.get("bookNum"), (Long) orderData.get("purchaseNum"),
            (Double) orderData.get("turnover"), (BigDecimal) bookData.get("visitNum"));
    }
}
