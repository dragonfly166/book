package com.book.domain;

import java.util.Date;
import lombok.Data;

/**
 * @author sunlongfei
 */
@Data
public class Order {

    private Integer id;

    private Integer userId;

    private Integer bookId;

    private Double price;

    private Date createTime;

    private Boolean isSuccess;

    private String address;
}
