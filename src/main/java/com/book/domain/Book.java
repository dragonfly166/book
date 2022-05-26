package com.book.domain;

import java.util.Date;
import lombok.Data;

/**
 * @author sunlongfei
 */
@Data
public class Book {

    private Integer id;

    private String name;

    private String description;

    private String image;

    private Integer publisherId;

    private Double price;

    private Integer watchNum;

    private Boolean sold;

    private Date createTime;
}
