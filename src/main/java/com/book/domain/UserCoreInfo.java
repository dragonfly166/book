package com.book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunlongfei
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCoreInfo {

    private Integer id;

    private String identity;
}
