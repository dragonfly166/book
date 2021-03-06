package com.book.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private Double cost;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    private Boolean isSuccess;

    private String address;
}
