package com.book.domain;

import java.util.Date;
import lombok.Data;

/**
 * @author sunlongfei
 */
@Data
public class Lack {

    private Integer id;

    private String bookName;

    private Integer publisherId;

    private Boolean isSolved;

    private Date createTime;
}
