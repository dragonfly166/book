package com.book.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

/**
 * @author sunlongfei
 */
@Data
public class OrderResult {

    private Integer id;

    private UserProfile user;

    private Book book;

    private Double cost;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    private Boolean isSuccess;

    private String address;

    public OrderResult(Integer id, Double cost, Date createTime,
        Boolean isSuccess, String address) {
        this.id = id;
        this.cost = cost;
        this.createTime = createTime;
        this.isSuccess = isSuccess;
        this.address = address;
    }
}
