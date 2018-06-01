package com.chero.client.domain;

import lombok.Data;

import java.util.Date;

@Data

public class CrLogger {
    private Integer id;
    private Date operateTime;
    private String operateContent;
    private String operateType;
    private String operator;
    private String operateAccount;
    private String userId;
    private String operation;
    private String operationBefore;
    private String operationAfter;

}
