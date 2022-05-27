package com.book.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

/**
 * @author sunlongfei
 */
@Data
public class User {

    private Integer id;

    private String username;

    private String password;

    private String email;

    private String sex;

    private String avatar;

    private String identity;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date registerTime;
}
