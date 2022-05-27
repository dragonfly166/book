package com.book.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author sunlongfei
 */
@Data
@AllArgsConstructor
public class LackResult {

    private Integer id;

    private String bookName;

    private UserProfile publisher;

    private Boolean isSolved;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;
}
