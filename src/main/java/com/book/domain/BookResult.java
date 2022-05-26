package com.book.domain;

import java.util.Date;

/**
 * @author sunlongfei
 */
public class BookResult {

    private Integer id;

    private String name;

    private String description;

    private String image;

    private UserProfile publisher;

    private Double price;

    private Integer watchNum;

    private Boolean sold;

    private Date createTime;

    public BookResult(Integer id, String name, String description, String image,
        UserProfile publisher, Double price, Integer watchNum, Boolean sold, Date createTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.publisher = publisher;
        this.price = price;
        this.watchNum = watchNum;
        this.sold = sold;
        this.createTime = createTime;
    }
}
