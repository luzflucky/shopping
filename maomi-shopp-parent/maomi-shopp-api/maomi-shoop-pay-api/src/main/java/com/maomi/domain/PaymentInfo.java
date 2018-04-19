package com.maomi.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentInfo {
    private Integer id;

    private Integer userid;

    private Integer typeid;

    private String orderid;

    private Long price;

    private String source;

    private Integer state;

    private Date created;

    private Date updated;

    private String platformorderid;

  
}