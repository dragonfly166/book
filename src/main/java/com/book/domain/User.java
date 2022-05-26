package com.book.domain;

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

    private Date registerTime;
}
