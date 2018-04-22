package com.maomi.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderInfo {
    private Integer id;

    private String ordernumber;

    private Integer ispay;

    private String payid;

    private Integer userid;

    private Date created;

    private Date updated;
}