package com.book.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;

/**
 * @author sunlongfei
 */
@AllArgsConstructor
@lombok.Data
public class Data {

    private Long userNum;

    private Long bookNum;

    private Long purchaseNum;

    private Double turnover;

    private BigDecimal visitNum;
}
